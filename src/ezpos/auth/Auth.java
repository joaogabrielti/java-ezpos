package ezpos.auth;

import ezpos.Main;
import ezpos.model.Usuario;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public abstract class Auth {
    private static Usuario usuarioAutenticado;

    public static Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    public static void setUsuarioAutenticado(Usuario usuarioAutenticado) {
        Auth.usuarioAutenticado = usuarioAutenticado;
    }

    public static void abrirJanelaFazerLogin(Stage stage) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();

        dialog.setTitle("EZPOS - Easy Point of Sale - 2021");
        dialog.setHeaderText("Entrar no Sistema");
        dialog.setGraphic(new ImageView(Objects.requireNonNull(Main.class.getResource("/assets/login.png")).toString()));

        ButtonType loginButtonType = new ButtonType("Entrar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField tfUsuario = new TextField();
        tfUsuario.setPromptText("Usuário");

        PasswordField tfSenha = new PasswordField();
        tfSenha.setPromptText("Senha");

        grid.add(new Label("Usuário:"), 0, 0);
        grid.add(tfUsuario, 1, 0);
        grid.add(new Label("Senha:"), 0, 1);
        grid.add(tfSenha, 1, 1);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        tfUsuario.textProperty().addListener((observable, oldValue, newValue) ->
            loginButton.setDisable(newValue.trim().isEmpty())
        );

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(tfUsuario::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(tfUsuario.getText(), tfSenha.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(dados -> {
            try {
                String usuario = dados.getKey();
                String senha = Hash.sha256(dados.getValue());

                Usuario u = Main.getUsuarioDAO().autenticar(usuario, senha);

                if (u != null) {
                    Auth.setUsuarioAutenticado(u);
                    Main.iniciarJanelaPrincipal(stage);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Senha incorreta!");
                    alert.showAndWait();
                    abrirJanelaFazerLogin(stage);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
