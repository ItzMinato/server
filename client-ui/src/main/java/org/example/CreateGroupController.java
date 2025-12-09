package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CreateGroupController {

    @FXML
    private TextField nameField;

    // Якщо додаси поле опису в FXML, просто розкоментуєш:
    // @FXML
    // private TextField descriptionField;

    @FXML
    private void onCreate() {
        try {
            String name = nameField.getText();

            if (name.isEmpty()) {
                showAlert("Error", "Group name cannot be empty!");
                return;
            }

            // Тимчасова заглушка — опис поки порожній
            String description = "";

            // Тимчасово: creatorId = 3 (teacher1)
            long creatorId = 3;

            // ВИКЛИКАЄМО НОВУ ВЕРСІЮ API
            String response = ApiClient.createGroup(name, description, creatorId);

            // Перевірка на помилку
            if (response.contains("\"status\":500") || response.toLowerCase().contains("error")) {
                showAlert("Error", "Server error:\n" + response);
                return;
            }

            showAlert("Success", "Group created:\n" + response);
            goBack();

        } catch (Exception e) {
            showAlert("Error", "Failed to create group:\n" + e.getMessage());
        }
    }

    @FXML
    private void onBack() {
        goBack();
    }

    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/groups-view.fxml"));
            Scene scene = new Scene(loader.load(), 600, 400);

            Stage stage = (Stage) nameField.getScene().getWindow();
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