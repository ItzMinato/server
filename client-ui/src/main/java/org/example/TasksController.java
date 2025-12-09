package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.List;

public class TasksController {

    @FXML
    private ListView<Task> taskList; // показує Task через toString()

    private long groupId;

    public void setGroupId(long id) {
        this.groupId = id;
        loadTasks();
    }

    // ==========================
    //       LOAD TASKS
    // ==========================
    private void loadTasks() {
        try {
            List<Task> tasks = ApiClient.getTasks(groupId);

            taskList.getItems().clear();
            taskList.getItems().addAll(tasks);

        } catch (Exception e) {
            show("Error", "Cannot load tasks:\n" + e.getMessage());
        }
    }

    // ==========================
    //       CREATE TASK
    // ==========================
    @FXML
    private void onCreateTask() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/create-task-view.fxml"));
            Scene scene = new Scene(loader.load(), 400, 300);

            CreateTaskController controller = loader.getController();
            controller.setGroupId(groupId); // передаємо groupId

            Stage stage = (Stage) taskList.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            show("Error", "Cannot open Create Task:\n" + e.getMessage());
        }
    }

    // ==========================
    //       OPEN TASK
    // ==========================
    @FXML
    private void onOpenTask() {
        Task selected = taskList.getSelectionModel().getSelectedItem();

        if (selected == null) {
            show("Error", "Please select a task first!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/task-details.fxml"));
            Scene scene = new Scene(loader.load(), 600, 400);

            TaskDetailsController controller = loader.getController();
            controller.setTask(selected); // передаємо TASK

            Stage stage = (Stage) taskList.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            show("Error", "Cannot open task:\n" + e.getMessage());
        }
    }

    // ==========================
    //        GO BACK
    // ==========================
    @FXML
    private void onBack() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/group-details.fxml"));
            Scene scene = new Scene(loader.load(), 600, 400);

            GroupDetailsController controller = loader.getController();
            controller.setGroupId(groupId);

            Stage stage = (Stage) taskList.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            show("Error", "Cannot return:\n" + e.getMessage());
        }
    }


    private void show(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
