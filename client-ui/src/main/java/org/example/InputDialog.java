package org.example;

import javafx.scene.control.TextInputDialog;

public class InputDialog {

    public static String ask(String message) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(message);
        dialog.setTitle("Input");
        dialog.setContentText(null);

        var result = dialog.showAndWait();
        return result.orElse(null);
    }
}
