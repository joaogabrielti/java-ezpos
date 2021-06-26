package ezpos.gui.control;

import ezpos.Main;
import ezpos.auth.Auth;
import ezpos.model.Cliente;
import ezpos.model.Venda;
import ezpos.repositories.interfaces.ClienteRepository;
import ezpos.repositories.interfaces.ProdutoRepository;
import ezpos.repositories.interfaces.VendaRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class JanelaAdicionarVenda extends JanelaBase {
    @FXML
    private ComboBox<Cliente> cbCliente;
    @FXML
    private ComboBox<String> cbFormaPagamento;
    @FXML
    private DatePicker dtData;

    private final VendaRepository vendaRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public JanelaAdicionarVenda(VendaRepository vendaRepository, ClienteRepository clienteRepository, ProdutoRepository produtoRepository) {
        this.vendaRepository = vendaRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @FXML
    private void initialize() {
        ArrayList<String> formasPagamentos = new ArrayList<>();
        formasPagamentos.add("DINHEIRO");
        formasPagamentos.add("CARTÃO CRÉDITO");
        formasPagamentos.add("CARTÃO DÉBITO");

        try {
            cbCliente.setItems(clienteRepository.listar());
            cbFormaPagamento.setItems(FXCollections.observableList(formasPagamentos));
            dtData.setValue(LocalDate.now());
        } catch (SQLException e) {
            e.printStackTrace();
            showDialogMessage(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    @FXML
    private void salvar() {
        Cliente cliente = cbCliente.getValue();
        String formaPagamento = cbFormaPagamento.getValue();
        LocalDate data = dtData.getValue();

        Venda venda = new Venda(cliente, Auth.getUsuarioAutenticado(), 0, formaPagamento, data, Collections.emptyList());

        int id = -1;
        try {
            id = vendaRepository.inserir(venda);
            if (id > 0) {
                venda.setId(id);
            } else {
                showDialogMessage(Alert.AlertType.ERROR, "Não foi possível cadastrar a compra!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showDialogMessage(Alert.AlertType.ERROR, e.getMessage());
        }

        if (id > 0) {
            Main.alterarJanela(Main.JANELA_ADICIONAR_VENDA_ITEM, aClass -> new JanelaAdicionarVendaItem(venda, vendaRepository, produtoRepository));
        } else {
            Main.voltaJanelaPrincipal();
        }
    }

    @FXML
    private void cancelar() {
        Main.voltaJanelaPrincipal();
    }
}
