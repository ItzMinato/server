@FXML
private void onCreate() {
    try {
        String name = nameField.getText();

        if (name.isEmpty()) {
            showAlert("Error", "Group name cannot be empty!");
            return;
        }

        String response = ApiClient.createGroup(name);

        showAlert("Success", "Group created:\n" + response);

        goBack();

    } catch (Exception e) {
        showAlert("Error", "Failed to create group:\n" + e.getMessage());
    }
}
