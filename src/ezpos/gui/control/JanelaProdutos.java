package ezpos.gui.control;

import ezpos.Main;
import ezpos.model.Produto;
import ezpos.repositories.interfaces.ProdutoRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import java.sql.SQLException;
import java.util.Optional;

public class JanelaProdutos extends JanelaBase {
    @FXML
    private ListView<Produto> ltvProdutos;

    private final ProdutoRepository produtoRepository;

    public JanelaProdutos(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @FXML
    private void initialize() {
        try {
            ltvProdutos.setItems(produtoRepository.listar());
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
        Main.alterarJanela(Main.JANELA_ADICIONAR_PRODUTO, aClass -> new JanelaAdicionarProduto(produtoRepository, null));
    }

    @FXML
    private void alterar() {
        Produto produtoSelecionado = ltvProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null) {
            Main.alterarJanela(Main.JANELA_ADICIONAR_PRODUTO, aClass -> new JanelaAdicionarProduto(produtoRepository, produtoSelecionado));
        } else {
            showDialogMessage(Alert.AlertType.ERROR, "Nenhum produto selecionado!");
        }
    }

    @FXML
    private void excluir() {
        Produto produtoSelecionado = ltvProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar Exclusão");
            alert.setHeaderText("Confirmação de Exclusão");
            alert.setContentText("Você tem certeza que deseja excluir este registro?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    produtoRepository.excluir(produtoSelecionado);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Main.alterarJanela(Main.JANELA_PRODUTOS, aClass -> new JanelaProdutos(produtoRepository));
            }
        } else {
            showDialogMessage(Alert.AlertType.ERROR, "Nenhum produto selecionado!");
        }
    }
}
