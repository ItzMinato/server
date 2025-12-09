package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ChoiceBox<String> roleChoice;

    @FXML
    public void initialize() {
        roleChoice.getItems().addAll("STUDENT", "TEACHER");
    }

    @FXML
    private void onRegister() {
        System.out.println("Register pressed!");
        // тут буде відправка на сервер
    }

    @FXML
    private void onBack() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    MainApp.class.getResource("/fxml/login-view.fxml")
            );
            Scene scene = new Scene(loader.load(), 400, 300);

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
