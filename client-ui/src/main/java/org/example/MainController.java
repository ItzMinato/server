package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private void initialize() {
        String username = CurrentUser.getUsername();
        welcomeLabel.setText("Welcome, " + username + "!");
    }

    @FXML
    private void onGroups() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/groups-view.fxml"));
            Scene scene = new Scene(loader.load(), 600, 400);

            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onLogout() {
        try {
            CurrentUser.clear(); // logout

            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/login-view.fxml"));
            Scene scene = new Scene(loader.load(), 400, 300);

            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
