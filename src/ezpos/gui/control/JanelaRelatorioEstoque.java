package ezpos.gui.control;

import ezpos.Main;
import ezpos.model.Produto;
import ezpos.repositories.interfaces.ProdutoRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import java.sql.SQLException;

public class JanelaRelatorioEstoque extends JanelaBase {
    @FXML
    private TextArea textArea;

    private final ProdutoRepository produtoRepository;

    public JanelaRelatorioEstoque(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @FXML
    private void initialize() {
        try {
            textArea.setText("ID\tNOME\tESTOQUE\n");
            for (Produto p : this.produtoRepository.listar()) {
                textArea.setText(textArea.getText() + p.getEstoqueString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showDialogMessage(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    @FXML
    private void imprimir() {
        // TODO("Impressão de relatórios não disponível.")
    }

    @FXML
    private void voltar() {
        Main.voltaJanelaPrincipal();
    }
}
