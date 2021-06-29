package ezpos.gui.control;

import ezpos.Main;
import ezpos.model.Compra;
import ezpos.model.Produto;
import ezpos.repositories.interfaces.CompraRepository;
import ezpos.repositories.interfaces.FornecedorRepository;
import ezpos.repositories.interfaces.ProdutoRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import java.sql.SQLException;
import java.util.Optional;

public class JanelaCompras extends JanelaBase {
    @FXML
    private ListView<Compra> ltvCompras;

    private final CompraRepository compraRepository;
    private final FornecedorRepository fornecedorRepository;
    private final ProdutoRepository produtoRepository;

    public JanelaCompras(CompraRepository compraRepository, FornecedorRepository fornecedorRepository, ProdutoRepository produtoRepository) {
        this.compraRepository = compraRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.produtoRepository = produtoRepository;
    }

    @FXML
    private void initialize() {
        try {
            ltvCompras.setItems(compraRepository.listar());
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
        Main.alterarJanela(Main.JANELA_ADICIONAR_COMPRA, aClass -> new JanelaAdicionarCompra(compraRepository, fornecedorRepository, produtoRepository, null));
    }

    @FXML
    private void alterar() {
        Compra compraSelecionada = ltvCompras.getSelectionModel().getSelectedItem();
        if (compraSelecionada != null) {
            Main.alterarJanela(Main.JANELA_ADICIONAR_COMPRA, aClass -> new JanelaAdicionarCompra(compraRepository, fornecedorRepository, produtoRepository, compraSelecionada));
        } else {
            showDialogMessage(Alert.AlertType.ERROR, "Nenhuma compra selecionada!");
        }
    }

    @FXML
    private void excluir() {
        Compra compraSelecionada = ltvCompras.getSelectionModel().getSelectedItem();
        if (compraSelecionada != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar Exclusão");
            alert.setHeaderText("Confirmação de Exclusão");
            alert.setContentText("Você tem certeza que deseja excluir este registro?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    compraRepository.excluir(compraSelecionada);
                    for (Produto p: produtoRepository.listar()) {
                        if (produtoRepository.atualizarEstoque(p)) {
                            showDialogMessage(Alert.AlertType.ERROR, "Ocorreu um erro ao tentar atualizar o estoque do produto: " + p.getNome() + ".");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Main.alterarJanela(Main.JANELA_COMPRAS, aClass -> new JanelaCompras(compraRepository, fornecedorRepository, produtoRepository));
            }
        } else {
            showDialogMessage(Alert.AlertType.ERROR, "Nenhuma compra selecionada!");
        }
    }
}
