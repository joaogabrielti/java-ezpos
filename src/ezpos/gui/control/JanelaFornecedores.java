package ezpos.gui.control;

import ezpos.Main;
import ezpos.model.Fornecedor;
import ezpos.repositories.interfaces.FornecedorRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import java.sql.SQLException;
import java.util.Optional;

public class JanelaFornecedores extends JanelaBase {
    @FXML
    private ListView<Fornecedor> ltvFornecedores;

    private final FornecedorRepository fornecedorRepository;

    public JanelaFornecedores(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    @FXML
    private void initialize() {
        try {
            ltvFornecedores.setItems(fornecedorRepository.listar());
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
        Main.alterarJanela(Main.JANELA_ADICIONAR_FORNECEDOR, aClass -> new JanelaAdicionarFornecedor(fornecedorRepository, null));
    }

    @FXML
    private void alterar() {
        Fornecedor fornecedorSelecionado = ltvFornecedores.getSelectionModel().getSelectedItem();
        if (fornecedorSelecionado != null) {
            Main.alterarJanela(Main.JANELA_ADICIONAR_FORNECEDOR, aClass -> new JanelaAdicionarFornecedor(fornecedorRepository, fornecedorSelecionado));
        } else {
            showDialogMessage(Alert.AlertType.ERROR, "Nenhum fornecedor selecionado!");
        }
    }

    @FXML
    private void excluir() {
        Fornecedor fornecedorSelecionado = ltvFornecedores.getSelectionModel().getSelectedItem();
        if (fornecedorSelecionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar Exclusão");
            alert.setHeaderText("Confirmação de Exclusão");
            alert.setContentText("Você tem certeza que deseja excluir este registro?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    fornecedorRepository.excluir(fornecedorSelecionado);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Main.alterarJanela(Main.JANELA_FORNECEDORES, aClass -> new JanelaFornecedores(fornecedorRepository));
            }
        } else {
            showDialogMessage(Alert.AlertType.ERROR, "Nenhum fornecedor selecionado!");
        }
    }
}
