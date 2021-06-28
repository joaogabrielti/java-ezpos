package ezpos.gui.control;

import ezpos.Main;
import ezpos.model.Cliente;
import ezpos.repositories.interfaces.ClienteRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import java.sql.SQLException;
import java.util.Optional;

public class JanelaClientes extends JanelaBase {
    @FXML
    private ListView<Cliente> ltvClientes;

    private final ClienteRepository clienteRepository;

    public JanelaClientes(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @FXML
    private void initialize() {
        try {
            ltvClientes.setItems(clienteRepository.listar());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void voltar() {
        Main.voltaJanelaPrincipal();
    }

    @FXML
    private void adicionar() {
        Main.alterarJanela(Main.JANELA_ADICIONAR_CLIENTE, aClass -> new JanelaAdicionarCliente(clienteRepository, null));
    }

    @FXML
    private void alterar() {
        Cliente clienteSelecionado = ltvClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            Main.alterarJanela(Main.JANELA_ADICIONAR_CLIENTE, aClass -> new JanelaAdicionarCliente(clienteRepository, clienteSelecionado));
        } else {
            showDialogMessage(Alert.AlertType.ERROR, "Nenhum cliente selecionado!");
        }
    }

    @FXML
    private void excluir() {
        Cliente clienteSelecionado = ltvClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar Exclusão");
            alert.setHeaderText("Confirmação de Exclusão");
            alert.setContentText("Você tem certeza que deseja excluir este registro?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    clienteRepository.excluir(clienteSelecionado);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Main.alterarJanela(Main.JANELA_CLIENTES, aClass -> new JanelaClientes(clienteRepository));
            }
        } else {
            showDialogMessage(Alert.AlertType.ERROR, "Nenhum cliente selecionado!");
        }
    }
}
