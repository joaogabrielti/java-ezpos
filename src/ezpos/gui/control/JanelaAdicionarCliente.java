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

    public JanelaAdicionarCliente(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @FXML
    private void salvar() {
        String cpfCnpj = tfCpfCnpj.getText();
        String nome = tfNome.getText();
        String endereco = tfEndereco.getText();
        String telefone = tfTelefone.getText();
        String email = tfEmail.getText();

        Cliente cliente = new Cliente(cpfCnpj, nome, endereco, telefone, email);

        try {
            if (clienteRepository.inserir(cliente)) {
                showDialogMessage(Alert.AlertType.INFORMATION, "Cliente cadastrado com sucesso!");
            } else {
                showDialogMessage(Alert.AlertType.ERROR, "Não foi possível cadastrar o cliente!");
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
