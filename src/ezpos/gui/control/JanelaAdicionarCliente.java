package ezpos.gui.control;

import ezpos.Main;
import ezpos.model.Cliente;
import ezpos.repositories.interfaces.ClienteRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.sql.SQLException;

public class JanelaAdicionarCliente extends JanelaBase {
    @FXML
    private TextField tfCpfCnpj;
    @FXML
    private TextField tfNome;
    @FXML
    private TextField tfEndereco;
    @FXML
    private TextField tfTelefone;
    @FXML
    private TextField tfEmail;

    private final ClienteRepository clienteRepository;
    private final Cliente clienteOriginal;

    public JanelaAdicionarCliente(ClienteRepository clienteRepository, Cliente clienteOriginal) {
        this.clienteRepository = clienteRepository;
        this.clienteOriginal = clienteOriginal;
    }

    @FXML
    private void initialize() {
        if (clienteOriginal != null) {
            tfCpfCnpj.setText(clienteOriginal.getCpfCnpj());
            tfNome.setText(clienteOriginal.getNome());
            tfEndereco.setText(clienteOriginal.getEndereco());
            tfTelefone.setText(clienteOriginal.getTelefone());
            tfEmail.setText(clienteOriginal.getEmail());
        }
    }

    @FXML
    private void salvar() {
        String cpfCnpj = tfCpfCnpj.getText();
        String nome = tfNome.getText();
        String endereco = tfEndereco.getText();
        String telefone = tfTelefone.getText();
        String email = tfEmail.getText();

        Cliente cliente = new Cliente(cpfCnpj, nome, endereco, telefone, email);
        if (clienteOriginal != null) {
            cliente.setId(clienteOriginal.getId());
        }

        try {
            if (clienteOriginal != null) {
                if (clienteRepository.editar(cliente)) {
                    showDialogMessage(Alert.AlertType.INFORMATION, "Cliente alterado com sucesso!");
                } else {
                    showDialogMessage(Alert.AlertType.ERROR, "Não foi possível alterar o cliente!");
                }
            } else {
                if (clienteRepository.inserir(cliente)) {
                    showDialogMessage(Alert.AlertType.INFORMATION, "Cliente cadastrado com sucesso!");
                } else {
                    showDialogMessage(Alert.AlertType.ERROR, "Não foi possível cadastrar o cliente!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showDialogMessage(Alert.AlertType.ERROR, e.getMessage());
        }

        Main.alterarJanela(Main.JANELA_CLIENTES, aClass -> new JanelaClientes(clienteRepository));
    }

    @FXML
    private void cancelar() {
        Main.alterarJanela(Main.JANELA_CLIENTES, aClass -> new JanelaClientes(clienteRepository));
    }
}
