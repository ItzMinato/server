package org.example;

import com.google.gson.Gson;   // ← додано!
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void onLogin() {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                showAlert("Error", "Please enter username and password!");
                return;
            }

            String response = ApiClient.login(username, password);
            System.out.println("Server response: " + response);

            // Check for server error
            if (response.toLowerCase().contains("error")) {
                showAlert("Login failed", response);
                return;
            }

            // Convert JSON → UserResponse
            UserResponse user = new Gson().fromJson(response, UserResponse.class);

            // Save logged user
            CurrentUser.set(user.getId(), user.getUsername(), user.getRole());

            // Load main window
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/main-view.fxml"));
            Scene scene = new Scene(loader.load(), 600, 400);

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            showAlert("Error", "Login failed:\n" + e.getMessage());
        }
    }

    @FXML
    private void onGoToRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/register-view.fxml"));
            Scene scene = new Scene(loader.load(), 400, 350);

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            showAlert("Error", "Cannot open register window:\n" + e.getMessage());
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
