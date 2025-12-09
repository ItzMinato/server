package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> roleBox;

    @FXML
    private void initialize() {
        roleBox.getItems().addAll("STUDENT", "TEACHER");
    }

    @FXML
    private void onRegister() {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String role = roleBox.getValue();

            if (username.isEmpty() || password.isEmpty() || role == null) {
                showAlert("Error", "Please fill in all fields!");
                return;
            }

            String response = ApiClient.register(username, password, role);

            showAlert("Success", "User registered!\n" + response);

            goBackToLogin();

        } catch (Exception e) {
            showAlert("Error", "Registration failed:\n" + e.getMessage());
        }
    }

    @FXML
    private void onBack() {
        goBackToLogin();
    }

    private void goBackToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/login-view.fxml"));
            Scene scene = new Scene(loader.load(), 400, 300);

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
