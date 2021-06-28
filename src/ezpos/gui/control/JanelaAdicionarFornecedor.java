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
    private final Fornecedor fornecedorOriginal;

    public JanelaAdicionarFornecedor(FornecedorRepository fornecedorRepository, Fornecedor fornecedorOriginal) {
        this.fornecedorRepository = fornecedorRepository;
        this.fornecedorOriginal = fornecedorOriginal;
    }

    @FXML
    private void initialize() {
        if (fornecedorOriginal != null) {
            tfCpfCnpj.setText(fornecedorOriginal.getCpfCnpj());
            tfNome.setText(fornecedorOriginal.getNome());
            tfEndereco.setText(fornecedorOriginal.getEndereco());
            tfTelefone.setText(fornecedorOriginal.getTelefone());
            tfEmail.setText(fornecedorOriginal.getEmail());
        }
    }

    @FXML
    private void salvar() {
        String cpfCnpj = tfCpfCnpj.getText();
        String nome = tfNome.getText();
        String endereco = tfEndereco.getText();
        String telefone = tfTelefone.getText();
        String email = tfEmail.getText();

        Fornecedor fornecedor = new Fornecedor(cpfCnpj, nome, endereco, telefone, email);
        if (fornecedorOriginal != null) {
            fornecedor.setId(fornecedorOriginal.getId());
        }

        try {
            if (fornecedorOriginal != null) {
                if (fornecedorRepository.editar(fornecedor)) {
                    showDialogMessage(Alert.AlertType.INFORMATION, "Fornecedor alterado com sucesso!");
                } else {
                    showDialogMessage(Alert.AlertType.ERROR, "Não foi possível alterar o fornecedor!");
                }
            } else {
                if (fornecedorRepository.inserir(fornecedor)) {
                    showDialogMessage(Alert.AlertType.INFORMATION, "Fornecedor cadastrado com sucesso!");
                } else {
                    showDialogMessage(Alert.AlertType.ERROR, "Não foi possível cadastrar o fornecedor!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showDialogMessage(Alert.AlertType.ERROR, e.getMessage());
        }

        Main.alterarJanela(Main.JANELA_FORNECEDORES, aClass -> new JanelaFornecedores(fornecedorRepository));
    }

    @FXML
    private void cancelar() {
        Main.alterarJanela(Main.JANELA_FORNECEDORES, aClass -> new JanelaFornecedores(fornecedorRepository));
    }
}
