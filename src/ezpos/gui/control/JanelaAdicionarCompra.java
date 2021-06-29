package ezpos.gui.control;

import ezpos.Main;
import ezpos.auth.Auth;
import ezpos.model.Compra;
import ezpos.model.Fornecedor;
import ezpos.repositories.interfaces.CompraRepository;
import ezpos.repositories.interfaces.FornecedorRepository;
import ezpos.repositories.interfaces.ProdutoRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;

public class JanelaAdicionarCompra extends JanelaBase {
    @FXML
    private Button btVerItens;
    @FXML
    private ComboBox<Fornecedor> cbFornecedor;
    @FXML
    private DatePicker dtData;

    private final CompraRepository compraRepository;
    private final FornecedorRepository fornecedorRepository;
    private final ProdutoRepository produtoRepository;

    private final Compra compraOriginal;

    public JanelaAdicionarCompra(CompraRepository compraRepository, FornecedorRepository fornecedorRepository, ProdutoRepository produtoRepository, Compra compraOriginal) {
        this.compraRepository = compraRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.produtoRepository = produtoRepository;
        this.compraOriginal = compraOriginal;
    }

    @FXML
    private void initialize() {
        try {
            cbFornecedor.setItems(fornecedorRepository.listar());
            dtData.setValue(LocalDate.now());

            if (compraOriginal != null) {
                cbFornecedor.getSelectionModel().select(compraOriginal.getFornecedor());
                dtData.setValue(compraOriginal.getData());
                btVerItens.setDisable(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showDialogMessage(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    @FXML
    private void salvar() {
        Fornecedor fornecedor = cbFornecedor.getValue();
        LocalDate data = dtData.getValue();

        Compra compra = new Compra(fornecedor, Auth.getUsuarioAutenticado(), 0, data, Collections.emptyList());
        if (compraOriginal != null) {
            compra.setId(compraOriginal.getId());
            compra.setValor(compraOriginal.getValor());
            compra.setItems(compraOriginal.getItems());
        }

        int id = -1;
        try {
            if (compraOriginal != null) {
                if (compraRepository.editar(compra)) {
                    showDialogMessage(Alert.AlertType.INFORMATION, "Compra alterada com sucesso!");
                } else {
                    showDialogMessage(Alert.AlertType.ERROR, "Não foi possível alterar a compra!");
                }
            } else {
                id = compraRepository.inserir(compra);
                if (id > 0) {
                    compra.setId(id);
                } else {
                    showDialogMessage(Alert.AlertType.ERROR, "Não foi possível cadastrar a compra!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showDialogMessage(Alert.AlertType.ERROR, e.getMessage());
        }

        if (id > 0 && compraOriginal == null) {
            Main.alterarJanela(Main.JANELA_ADICIONAR_COMPRA_ITEM, aClass -> new JanelaAdicionarCompraItem(compra, compraRepository, produtoRepository));
        } else {
            Main.alterarJanela(Main.JANELA_COMPRAS, aClass -> new JanelaCompras(compraRepository, fornecedorRepository, produtoRepository));
        }
    }

    @FXML
    private void cancelar() {
        Main.alterarJanela(Main.JANELA_COMPRAS, aClass -> new JanelaCompras(compraRepository, fornecedorRepository, produtoRepository));
    }

    public void verItens() {
        Main.alterarJanela(Main.JANELA_COMPRAS_ITEMS, aClass -> new JanelaCompraItems(compraRepository, fornecedorRepository, produtoRepository, compraOriginal));
    }
}
