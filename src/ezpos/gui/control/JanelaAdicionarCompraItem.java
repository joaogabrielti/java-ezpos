package ezpos.gui.control;

import ezpos.Main;
import ezpos.model.Compra;
import ezpos.model.CompraItem;
import ezpos.model.Produto;
import ezpos.repositories.interfaces.CompraRepository;
import ezpos.repositories.interfaces.ProdutoRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class JanelaAdicionarCompraItem extends JanelaBase {
    @FXML
    private ComboBox<Produto> cbProduto;
    @FXML
    private TextField tfQuantidade;
    @FXML
    private TextArea areaItens;

    private final Compra compra;
    private final CompraRepository compraRepository;
    private final ProdutoRepository produtoRepository;

    public JanelaAdicionarCompraItem(Compra compra, CompraRepository compraRepository, ProdutoRepository produtoRepository) {
        this.compra = compra;
        this.compraRepository = compraRepository;
        this.produtoRepository = produtoRepository;
    }

    @FXML
    private void initialize() {
        try {
            cbProduto.setItems(this.produtoRepository.listar());
        } catch (SQLException e) {
            e.printStackTrace();
            showDialogMessage(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    @FXML
    private void adicionarItem() {
        Produto produto = cbProduto.getValue();
        double quantidade = Double.parseDouble(tfQuantidade.getText());

        CompraItem item = new CompraItem(this.compra, produto, quantidade, produto.getValor());

        try {
            if (compraRepository.inserirItem(item)) {
                areaItens.setText(areaItens.getText() + item + "\n");
            } else {
                showDialogMessage(Alert.AlertType.ERROR, "Não foi possível cadastrar o item!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showDialogMessage(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    @FXML
    private void finalizarCompra() {
        showDialogMessage(Alert.AlertType.INFORMATION, "Compra realizada com sucesso!");
        Main.voltaJanelaPrincipal();
    }
}
