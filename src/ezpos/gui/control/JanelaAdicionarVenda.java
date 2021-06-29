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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class JanelaAdicionarVenda extends JanelaBase {
    @FXML
    private Button btVerItens;
    @FXML
    private ComboBox<Cliente> cbCliente;
    @FXML
    private ComboBox<String> cbFormaPagamento;
    @FXML
    private DatePicker dtData;

    private final VendaRepository vendaRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    private final Venda vendaOriginal;

    public JanelaAdicionarVenda(VendaRepository vendaRepository, ClienteRepository clienteRepository, ProdutoRepository produtoRepository, Venda vendaOriginal) {
        this.vendaRepository = vendaRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.vendaOriginal = vendaOriginal;
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

            if (vendaOriginal != null) {
                cbCliente.getSelectionModel().select(vendaOriginal.getCliente());
                cbFormaPagamento.getSelectionModel().select(vendaOriginal.getFormaPagamento());
                dtData.setValue(vendaOriginal.getData());
                btVerItens.setDisable(false);
            }
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
        if (vendaOriginal != null) {
            venda.setId(vendaOriginal.getId());
            venda.setValor(vendaOriginal.getValor());
            venda.setItems(vendaOriginal.getItems());
        }

        int id = -1;
        try {
            if (vendaOriginal != null) {
                if (vendaRepository.editar(venda)) {
                    showDialogMessage(Alert.AlertType.INFORMATION, "Venda alterada com sucesso!");
                } else {
                    showDialogMessage(Alert.AlertType.ERROR, "Não foi possível alterar a venda!");
                }
            } else {
                id = vendaRepository.inserir(venda);
                if (id > 0) {
                    venda.setId(id);
                } else {
                    showDialogMessage(Alert.AlertType.ERROR, "Não foi possível cadastrar a venda!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showDialogMessage(Alert.AlertType.ERROR, e.getMessage());
        }

        if (id > 0 && vendaOriginal == null) {
            Main.alterarJanela(Main.JANELA_ADICIONAR_VENDA_ITEM, aClass -> new JanelaAdicionarVendaItem(venda, vendaRepository, produtoRepository));
        } else {
            Main.alterarJanela(Main.JANELA_VENDAS, aClass -> new JanelaVendas(vendaRepository, clienteRepository, produtoRepository));
        }
    }

    @FXML
    private void cancelar() {
        Main.voltaJanelaPrincipal();
    }

    @FXML
    private void verItens() {
        Main.alterarJanela(Main.JANELA_VENDAS_ITEMS, aClass -> new JanelaVendaItems(vendaRepository, clienteRepository, produtoRepository, vendaOriginal));
    }
}
