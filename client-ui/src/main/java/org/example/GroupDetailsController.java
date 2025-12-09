package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class GroupDetailsController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label descLabel;

    @FXML
    private Label creatorLabel;

    private long groupId;

    public void setGroupId(long id) {
        this.groupId = id;
        loadGroup();
    }

    private void loadGroup() {
        try {
            Group g = ApiClient.getGroupById(groupId);

            nameLabel.setText(g.getName());
            descLabel.setText(g.getDescription());
            creatorLabel.setText(String.valueOf(g.getCreatedBy()));

        } catch (Exception e) {
            nameLabel.setText("ERROR LOADING GROUP");
            e.printStackTrace();
        }
    }

    @FXML
    private void onBack() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/groups-view.fxml"));
            Scene scene = new Scene(loader.load(), 600, 400);

            Stage stage = (Stage) nameLabel.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void onOpenTasks() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/tasks-view.fxml"));
            Scene scene = new Scene(loader.load(), 600, 400);

            TasksController controller = loader.getController();
            controller.setGroupId(groupId);

            Stage stage = (Stage) nameLabel.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
