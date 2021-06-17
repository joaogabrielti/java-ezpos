package ezpos.gui.control;

import javafx.scene.control.Alert;

public class JanelaBase {
    protected void showDialogMessage(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message);
        alert.showAndWait();
    }
}
