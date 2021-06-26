package ezpos.gui.control;

import ezpos.Main;
import ezpos.model.Produto;
import ezpos.model.Venda;
import ezpos.model.VendaItem;
import ezpos.repositories.interfaces.ProdutoRepository;
import ezpos.repositories.interfaces.VendaRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.sql.SQLException;

public class JanelaAdicionarVendaItem extends JanelaBase {
    @FXML
    private ComboBox<Produto> cbProduto;
    @FXML
    private TextField tfQuantidade;
    @FXML
    private TextArea areaItens;

    private final Venda venda;
    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;

    public JanelaAdicionarVendaItem(Venda venda, VendaRepository vendaRepository, ProdutoRepository produtoRepository) {
        this.venda = venda;
        this.vendaRepository = vendaRepository;
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

        VendaItem item = new VendaItem(this.venda, produto, quantidade, produto.getValor());

        try {
            if (vendaRepository.inserirItem(item)) {
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
        showDialogMessage(Alert.AlertType.INFORMATION, "Venda realizada com sucesso!");
        Main.voltaJanelaPrincipal();
    }
}
