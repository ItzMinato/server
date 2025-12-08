package com.studyplatform.clientui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientApp extends Application {

    @Override
    public void start(Stage stage) {

        Label title = new Label("Login");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        TextArea output = new TextArea();
        output.setEditable(false);
        output.setPrefHeight(120);

        // ===== LOGIN BUTTON =====
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-font-size: 14px;");

        loginButton.setOnAction(event -> {
            output.clear();
            output.appendText(">>> Sending request...\n");

            String json = """
                    {
                        "username": "%s",
                        "password": "%s"
                    }
                    """.formatted(usernameField.getText(), passwordField.getText());

            String response = HttpClientHelper.sendPostRequest(
                    "http://localhost:8080/api/users/login",
                    json
            );

            output.appendText("Server: " + response + "\n");

            if (response.contains("Login successful")) {
                DashboardWindow dashboard = new DashboardWindow(usernameField.getText());
                dashboard.show();
                stage.close();
            }
        });

        // ===== REGISTER BUTTON =====
        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-font-size: 14px;");

        registerButton.setOnAction(event -> {
            RegisterWindow registerWindow = new RegisterWindow();
            registerWindow.show();
        });

        // ===== UI LAYOUT =====
        VBox layout = new VBox(12,
                title,
                usernameField,
                passwordField,
                loginButton,
                registerButton,
                output
        );

        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        stage.setScene(new Scene(layout, 350, 380));
        stage.setTitle("Study Platform — Login");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
