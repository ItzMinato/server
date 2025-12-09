package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TaskDetailsController {

    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label deadlineLabel;

    private Task task;

    public void setTask(Task task) {
        this.task = task;
        loadTask();
    }

    private void loadTask() {
        if (task == null) return;

        titleLabel.setText(task.getTitle());
        descriptionLabel.setText(task.getDescription());
        statusLabel.setText(task.getStatus());
        deadlineLabel.setText(task.getDeadline() != null ? task.getDeadline() : "No deadline");
    }

    // ==============================
    //       CHANGE STATUS
    // ==============================

    @FXML
    private void onSetOpen() { updateStatus("OPEN"); }

    @FXML
    private void onSetInProgress() { updateStatus("IN_PROGRESS"); }

    @FXML
    private void onSetDone() { updateStatus("DONE"); }

    private void updateStatus(String newStatus) {
        try {
            Task updated = ApiClient.updateTaskStatus(
                    task.getGroupId(),
                    task.getId(),
                    newStatus
            );

            this.task = updated;
            loadTask();

            show("Success", "Status changed to: " + newStatus);

        } catch (Exception e) {
            show("Error", "Cannot update status:\n" + e.getMessage());
        }
    }

    // ==============================
    //       BACK
    // ==============================

    @FXML
    private void onBack() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/tasks-view.fxml"));
            Scene scene = new Scene(loader.load(), 600, 400);

            TasksController controller = loader.getController();
            controller.setGroupId(task.getGroupId());

            Stage stage = (Stage) titleLabel.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void show(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
