package com.studyplatform.clientui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardWindow extends Stage {

    public DashboardWindow(String username) {
        setTitle("Study Platform — Dashboard");

        Label welcome = new Label("Welcome, " + username + "!");
        welcome.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        VBox root = new VBox(10, welcome);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 400, 250);
        setScene(scene);
    }
}
