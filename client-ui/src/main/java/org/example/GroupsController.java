package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.util.List;

public class GroupsController {

    @FXML
    private ListView<String> groupsList;

    @FXML
    private void initialize() {
        loadGroups();
    }

    private void loadGroups() {
        try {
            List<Group> groups = ApiClient.getUserGroups();

            groupsList.getItems().clear();

            for (Group g : groups) {
                groupsList.getItems().add(g.getName());
            }

        } catch (Exception e) {
            showAlert("Error", "Failed to load groups:\n" + e.getMessage());
        }
    }

    @FXML
    private void onCreateGroup() {
        showAlert("Info", "Create Group clicked.");
    }

    @FXML
    private void onOpenGroup() {
        String selected = groupsList.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Error", "Please select a group.");
            return;
        }

        showAlert("Info", "Opening group: " + selected);
    }

    @FXML
    private void onBack() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/main-view.fxml"));
            Scene scene = new Scene(loader.load(), 600, 400);

            Stage stage = (Stage) groupsList.getScene().getWindow();
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
