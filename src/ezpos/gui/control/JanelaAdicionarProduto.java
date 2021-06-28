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
    private TextField tfValorCompra;
    @FXML
    private TextField tfValorVenda;

    private final ProdutoRepository produtoRepository;
    private final Produto produtoOriginal;

    public JanelaAdicionarProduto(ProdutoRepository produtoRepository, Produto produtoOriginal) {
        this.produtoRepository = produtoRepository;
        this.produtoOriginal = produtoOriginal;
    }

    @FXML
    private void initialize() {
        if (produtoOriginal != null) {
            tfNome.setText(produtoOriginal.getNome());
            tfDescricao.setText(produtoOriginal.getDescricao());
            tfValorCompra.setText(Double.toString(produtoOriginal.getValorCompra()));
            tfValorVenda.setText(Double.toString(produtoOriginal.getValorVenda()));
        }
    }

    @FXML
    private void salvar() {
        try {
            String nome = tfNome.getText();
            String descricao = tfDescricao.getText();
            double valor_compra = Double.parseDouble(tfValorCompra.getText());
            double valor_venda = Double.parseDouble(tfValorVenda.getText());

            Produto produto = new Produto(nome, descricao, 0, valor_compra, valor_venda);
            if (produtoOriginal != null) {
                produto.setId(produtoOriginal.getId());
            }

            if (produtoOriginal != null) {
                if (produtoRepository.editar(produto)) {
                    showDialogMessage(Alert.AlertType.INFORMATION, "Produto alterado com sucesso!");
                } else {
                    showDialogMessage(Alert.AlertType.ERROR, "Não foi possível alterar o produto!");
                }
            } else {
                if (produtoRepository.inserir(produto)) {
                    showDialogMessage(Alert.AlertType.INFORMATION, "Produto cadastrado com sucesso!");
                } else {
                    showDialogMessage(Alert.AlertType.ERROR, "Não foi possível cadastrar o produto!");
                }
            }

            Main.alterarJanela(Main.JANELA_PRODUTOS, aClass -> new JanelaProdutos(produtoRepository));
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            showDialogMessage(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    @FXML
    private void cancelar() {
        Main.alterarJanela(Main.JANELA_PRODUTOS, aClass -> new JanelaProdutos(produtoRepository));
    }
}
