package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import javafx.stage.FileChooser;



public class ResourcesController {

    @FXML
    private ListView<Resource> resourceList;

    private long groupId;

    public void setGroupId(long id) {
        this.groupId = id;
        loadResources();
    }

    // ============= LOAD RESOURCES =============
    private void loadResources() {
        try {
            List<Resource> data = ApiClient.getResources(groupId); // FIXED
            resourceList.getItems().setAll(data);
        } catch (Exception e) {
            show("Error", "Failed to load resources:\n" + e.getMessage());
        }
    }

    // ============= ADD RESOURCE =============
    @FXML
    private void onAddResource() {

        String title = InputDialog.ask("Resource Title:");
        if (title == null || title.isEmpty()) return;

        // Запитати тип ресурсу
        String type = InputDialog.ask("Type 'file' or 'url':");
        if (type == null) return;

        try {
            if (type.equalsIgnoreCase("url")) {

                String url = InputDialog.ask("Resource URL:");
                if (url == null || url.isEmpty()) return;

                ApiClient.createResourceLink(groupId, title, url);

            } else if (type.equalsIgnoreCase("file")) {

                FileChooser chooser = new FileChooser();
                chooser.setTitle("Select file");
                File file = chooser.showOpenDialog(resourceList.getScene().getWindow());

                if (file == null) return;

                ApiClient.uploadResourceMultipart(groupId, title, file);

            } else {
                show("Error", "Unknown type! Enter 'file' or 'url'");
                return;
            }

            loadResources();

        } catch (Exception e) {
            show("Error", "Cannot create resource:\n" + e.getMessage());
        }
    }


    @FXML
    private void onOpenResource() {
        Resource r = resourceList.getSelectionModel().getSelectedItem();
        if (r == null) {
            show("Error", "Select a resource first!");
            return;
        }

        try {
            // Якщо це URL
            if (r.getUrl() != null) {
                Desktop.getDesktop().browse(new URI(r.getUrl()));
                return;
            }

            // Якщо це файл
            if (r.getFileBase64() != null) {

                byte[] data = Base64.getDecoder().decode(r.getFileBase64());

                File temp = new File(System.getProperty("java.io.tmpdir"), r.getFileName());
                Files.write(temp.toPath(), data);

                Desktop.getDesktop().open(temp); // відкриває PDF, DOCX, JPG і т.д.
                return;
            }

            show("Error", "This resource has no valid data!");

        } catch (Exception e) {
            show("Error", "Cannot open resource:\n" + e.getMessage());
        }
    }


    // ============= BACK TO GROUP DETAILS =============
    @FXML
    private void onBack() {
        try {
            FXMLLoader loader =
                    new FXMLLoader(MainApp.class.getResource("/fxml/group-details.fxml"));

            Scene scene = new Scene(loader.load(), 600, 400);

            GroupDetailsController controller = loader.getController();
            controller.setGroupId(groupId);

            Stage stage = (Stage) resourceList.getScene().getWindow();
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
