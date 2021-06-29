package ezpos.gui.control;

import ezpos.Main;
import ezpos.model.Venda;
import ezpos.model.VendaItem;
import ezpos.repositories.interfaces.ClienteRepository;
import ezpos.repositories.interfaces.ProdutoRepository;
import ezpos.repositories.interfaces.VendaRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import java.sql.SQLException;
import java.util.Optional;

public class JanelaVendaItems extends JanelaBase {
    @FXML
    private ListView<VendaItem> ltvItens;

    private final VendaRepository vendaRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final Venda venda;

    public JanelaVendaItems(VendaRepository vendaRepository, ClienteRepository clienteRepository, ProdutoRepository produtoRepository, Venda venda) {
        this.vendaRepository = vendaRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.venda = venda;
    }

    @FXML
    private void initialize() {
        try {
            ltvItens.setItems(vendaRepository.listarItems(venda));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void voltar() {
        Main.alterarJanela(Main.JANELA_VENDAS, aClass -> new JanelaVendas(vendaRepository, clienteRepository, produtoRepository));
    }

    @FXML
    private void excluir() {
        VendaItem itemSelecionado = ltvItens.getSelectionModel().getSelectedItem();
        if (itemSelecionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar Exclusão");
            alert.setHeaderText("Confirmação de Exclusão");
            alert.setContentText("Você tem certeza que deseja excluir este registro?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    if (vendaRepository.excluirItem(itemSelecionado)) {
                        showDialogMessage(Alert.AlertType.INFORMATION, "Item excluído com sucesso!");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Main.alterarJanela(Main.JANELA_VENDAS_ITEMS, aClass -> new JanelaVendaItems(vendaRepository, clienteRepository, produtoRepository, venda));
            }
        } else {
            showDialogMessage(Alert.AlertType.ERROR, "Nenhum item selecionado!");
        }
    }
}
