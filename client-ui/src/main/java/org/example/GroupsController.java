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
    private ListView<Group> groupsList;

    @FXML
    private void initialize() {
        loadGroups();
    }

    private void loadGroups() {
        try {
            List<Group> groups = ApiClient.getUserGroups();

            groupsList.getItems().clear();
            groupsList.getItems().addAll(groups);

        } catch (Exception e) {
            showAlert("Error", "Failed to load groups:\n" + e.getMessage());
        }
    }

    @FXML
    private void onCreateGroup() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/create-group-view.fxml"));
            Scene scene = new Scene(loader.load(), 400, 200);

            Stage stage = (Stage) groupsList.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Cannot open Create Group window:\n" + e.getMessage());
        }
    }

    @FXML
    private void onOpenGroup() {
        Group selected = groupsList.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Error", "Please select a group first!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/group-details.fxml"));
            Scene scene = new Scene(loader.load(), 600, 400);

            GroupDetailsController controller = loader.getController();
            controller.setGroupId(selected.getId());

            Stage stage = (Stage) groupsList.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            showAlert("Error", "Cannot open group:\n" + e.getMessage());
            e.printStackTrace();
        }
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

    // ✅ ТЕПЕР ЦЕ ОКРЕМИЙ МЕТОД
    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
