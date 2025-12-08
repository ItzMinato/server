package com.studyplatform.clientui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterWindow {

    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Register");

        Label title = new Label("Create Account");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField username = new TextField();
        username.setPromptText("Username");

        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        PasswordField confirm = new PasswordField();
        confirm.setPromptText("Repeat password");

        TextArea output = new TextArea();
        output.setEditable(false);
        output.setPrefHeight(100);

        Button registerBtn = new Button("Register");

        registerBtn.setOnAction(e -> {
            output.clear();

            if (!password.getText().equals(confirm.getText())) {
                output.setText("Passwords do not match!");
                return;
            }

            String json = """
                    {
                        "username": "%s",
                        "password": "%s"
                    }
                    """.formatted(username.getText(), password.getText());

            String response = HttpClientHelper.sendPostRequest(
                    "http://localhost:8080/api/users/register",
                    json
            );

            output.appendText("Server: " + response);

            if (response.contains("success")) {
                stage.close();
            }
        });

        VBox root = new VBox(12, title, username, password, confirm, registerBtn, output);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        stage.setScene(new Scene(root, 350, 350));
        stage.show();
    }
}
