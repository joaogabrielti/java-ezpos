package ezpos.gui.control;

import ezpos.Main;
import ezpos.model.Compra;
import ezpos.model.CompraItem;
import ezpos.repositories.interfaces.CompraRepository;
import ezpos.repositories.interfaces.FornecedorRepository;
import ezpos.repositories.interfaces.ProdutoRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import java.sql.SQLException;
import java.util.Optional;

public class JanelaCompraItems extends JanelaBase {
    @FXML
    private ListView<CompraItem> ltvItens;

    private final CompraRepository compraRepository;
    private final FornecedorRepository fornecedorRepository;
    private final ProdutoRepository produtoRepository;
    private final Compra compra;

    public JanelaCompraItems(CompraRepository compraRepository, FornecedorRepository fornecedorRepository, ProdutoRepository produtoRepository, Compra compra) {
        this.compraRepository = compraRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.produtoRepository = produtoRepository;
        this.compra = compra;
    }

    @FXML
    private void initialize() {
        try {
            ltvItens.setItems(compraRepository.listarItems(compra));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void voltar() {
        Main.alterarJanela(Main.JANELA_COMPRAS, aClass -> new JanelaCompras(compraRepository, fornecedorRepository, produtoRepository));
    }

    @FXML
    private void excluir() {
        CompraItem itemSelecionado = ltvItens.getSelectionModel().getSelectedItem();
        if (itemSelecionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar Exclusão");
            alert.setHeaderText("Confirmação de Exclusão");
            alert.setContentText("Você tem certeza que deseja excluir este registro?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    if (compraRepository.excluirItem(itemSelecionado)) {
                        showDialogMessage(Alert.AlertType.INFORMATION, "Item excluído com sucesso!");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Main.alterarJanela(Main.JANELA_COMPRAS_ITEMS, aClass -> new JanelaCompraItems(compraRepository, fornecedorRepository, produtoRepository, compra));
            }
        } else {
            showDialogMessage(Alert.AlertType.ERROR, "Nenhum item selecionado!");
        }
    }
}
