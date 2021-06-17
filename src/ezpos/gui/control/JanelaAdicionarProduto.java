package ezpos.gui.control;

import ezpos.Main;
import ezpos.model.Produto;
import ezpos.repositories.interfaces.ProdutoRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.sql.SQLException;

public class JanelaAdicionarProduto extends JanelaBase {
    @FXML
    private TextField tfNome;
    @FXML
    private TextField tfDescricao;
    @FXML
    private TextField tfValor;

    private final ProdutoRepository produtoRepository;

    public JanelaAdicionarProduto(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @FXML
    private void salvar() {
        String nome = tfNome.getText();
        String descricao = tfDescricao.getText();
        double valor = Double.parseDouble(tfValor.getText());

        Produto produto = new Produto(nome, descricao, 0, valor);

        try {
            if (produtoRepository.inserir(produto)) {
                showDialogMessage(Alert.AlertType.INFORMATION, "Produto cadastrado com sucesso!");
            } else {
                showDialogMessage(Alert.AlertType.ERROR, "Não foi possível cadastrar o produto!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showDialogMessage(Alert.AlertType.ERROR, e.getMessage());
        }

        Main.voltaJanelaPrincipal();
    }

    @FXML
    private void cancelar() {
        Main.voltaJanelaPrincipal();
    }
}
