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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;

public class JanelaAdicionarCompra extends JanelaBase {
    @FXML
    private ComboBox<Fornecedor> cbFornecedor;
    @FXML
    private DatePicker dtData;

    private final CompraRepository compraRepository;
    private final FornecedorRepository fornecedorRepository;
    private final ProdutoRepository produtoRepository;

    public JanelaAdicionarCompra(CompraRepository compraRepository, FornecedorRepository fornecedorRepository, ProdutoRepository produtoRepository) {
        this.compraRepository = compraRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.produtoRepository = produtoRepository;
    }

    @FXML
    private void initialize() {
        try {
            cbFornecedor.setItems(fornecedorRepository.listar());
            dtData.setValue(LocalDate.now());
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

        int id = -1;
        try {
            id = compraRepository.inserir(compra);
            if (id > 0) {
                compra.setId(id);
            } else {
                showDialogMessage(Alert.AlertType.ERROR, "Não foi possível cadastrar a compra!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showDialogMessage(Alert.AlertType.ERROR, e.getMessage());
        }

        if (id > 0) {
            Main.alterarJanela(Main.JANELA_ADICIONAR_COMPRA_ITEM, aClass -> new JanelaAdicionarCompraItem(compra, compraRepository, produtoRepository));
        } else {
            Main.voltaJanelaPrincipal();
        }
    }

    @FXML
    private void cancelar() {
        Main.voltaJanelaPrincipal();
    }
}
