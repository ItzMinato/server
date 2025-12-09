package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateTaskController {

    @FXML
    private TextField titleField;

    @FXML
    private TextField descField;

    private long groupId;

    public void setGroupId(long id) {
        this.groupId = id;
    }

    @FXML
    private void onCreate() {
        try {
            String title = titleField.getText();
            String desc = descField.getText();

            if (title.isEmpty()) {
                showAlert("Error", "Title cannot be empty!");
                return;
            }

            String response = ApiClient.createTask(groupId, title, desc);

            showAlert("Success", "Task created:\n" + response);

            onBack();

        } catch (Exception e) {
            showAlert("Error", "Failed to create task:\n" + e.getMessage());
        }
    }

    @FXML
    private void onBack() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/tasks-view.fxml"));
            Scene scene = new Scene(loader.load(), 600, 400);

            TasksController ctrl = loader.getController();
            ctrl.setGroupId(groupId);

            Stage stage = (Stage) titleField.getScene().getWindow();
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
