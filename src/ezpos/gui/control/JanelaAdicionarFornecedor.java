package ezpos.gui.control;

import ezpos.Main;
import ezpos.model.Fornecedor;
import ezpos.repositories.interfaces.FornecedorRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.sql.SQLException;

public class JanelaAdicionarFornecedor extends JanelaBase {
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

    private final FornecedorRepository fornecedorRepository;

    public JanelaAdicionarFornecedor(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    @FXML
    private void salvar() {
        String cpfCnpj = tfCpfCnpj.getText();
        String nome = tfNome.getText();
        String endereco = tfEndereco.getText();
        String telefone = tfTelefone.getText();
        String email = tfEmail.getText();

        Fornecedor fornecedor = new Fornecedor(cpfCnpj, nome, endereco, telefone, email);

        try {
            if (fornecedorRepository.inserir(fornecedor)) {
                showDialogMessage(Alert.AlertType.INFORMATION, "Fornecedor cadastrado com sucesso!");
            } else {
                showDialogMessage(Alert.AlertType.ERROR, "Não foi possível cadastrar o fornecedor!");
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
