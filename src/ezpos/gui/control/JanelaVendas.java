package ezpos.gui.control;

import ezpos.Main;
import ezpos.model.Produto;
import ezpos.model.Venda;
import ezpos.repositories.interfaces.ClienteRepository;
import ezpos.repositories.interfaces.ProdutoRepository;
import ezpos.repositories.interfaces.VendaRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import java.sql.SQLException;
import java.util.Optional;

public class JanelaVendas extends JanelaBase {
    @FXML
    private ListView<Venda> ltvVendas;

    private final VendaRepository vendaRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public JanelaVendas(VendaRepository vendaRepository, ClienteRepository clienteRepository, ProdutoRepository produtoRepository) {
        this.vendaRepository = vendaRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @FXML
    private void initialize() {
        try {
            ltvVendas.setItems(vendaRepository.listar());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void voltar() {
        Main.voltaJanelaPrincipal();
    }

    @FXML
    private void adicionar() {
        Main.alterarJanela(Main.JANELA_ADICIONAR_VENDA, aClass -> new JanelaAdicionarVenda(vendaRepository, clienteRepository, produtoRepository, null));
    }

    @FXML
    private void alterar() {
        Venda vendaSelecionada = ltvVendas.getSelectionModel().getSelectedItem();
        if (vendaSelecionada != null) {
            Main.alterarJanela(Main.JANELA_ADICIONAR_VENDA, aClass -> new JanelaAdicionarVenda(vendaRepository, clienteRepository, produtoRepository, vendaSelecionada));
        } else {
            showDialogMessage(Alert.AlertType.ERROR, "Nenhuma venda selecionada!");
        }
    }

    @FXML
    private void excluir() {
        Venda vendaSelecionada = ltvVendas.getSelectionModel().getSelectedItem();
        if (vendaSelecionada != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar Exclusão");
            alert.setHeaderText("Confirmação de Exclusão");
            alert.setContentText("Você tem certeza que deseja excluir este registro?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    vendaRepository.excluir(vendaSelecionada);
                    for (Produto p: produtoRepository.listar()) {
                        if (produtoRepository.atualizarEstoque(p)) {
                            showDialogMessage(Alert.AlertType.ERROR, "Ocorreu um erro ao tentar atualizar o estoque do produto: " + p.getNome() + ".");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Main.alterarJanela(Main.JANELA_VENDAS, aClass -> new JanelaVendas(vendaRepository, clienteRepository, produtoRepository));
            }
        } else {
            showDialogMessage(Alert.AlertType.ERROR, "Nenhuma venda selecionada!");
        }
    }
}
