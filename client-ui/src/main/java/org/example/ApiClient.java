package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.Arrays;
import java.util.List;

public class ApiClient {

    private static final String BASE_URL = "http://localhost:8080/api";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

    // ============================
    //        AUTH
    // ============================

    public static String register(String username, String password, String role) throws Exception {
        JsonObject json = new JsonObject();
        json.addProperty("username", username);
        json.addProperty("password", password);
        json.addProperty("role", role);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/users/register"))
                .header("Content-Type", "application/json; charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public static String login(String username, String password) throws Exception {
        JsonObject json = new JsonObject();
        json.addProperty("username", username);
        json.addProperty("password", password);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/users/login"))
                .header("Content-Type", "application/json; charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }


    // ============================
    //        GROUPS
    // ============================

    public static List<Group> getUserGroups() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/groups"))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        String body = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        Group[] arr = gson.fromJson(body, Group[].class);

        return Arrays.asList(arr);
    }

    public static String createGroup(String name, String description, long creatorId) throws Exception {
        JsonObject json = new JsonObject();
        json.addProperty("name", name);
        json.addProperty("description", description);
        json.addProperty("creatorId", creatorId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/groups"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public static Group getGroupById(long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/groups"))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        String body = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        Group[] groups = gson.fromJson(body, Group[].class);

        return Arrays.stream(groups)
                .filter(g -> g.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Group not found"));
    }


    // ============================
    //        TASKS
    // ============================

    public static List<Task> getTasks(long groupId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/groups/" + groupId + "/tasks"))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        String body = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        Task[] arr = gson.fromJson(body, Task[].class);

        return Arrays.asList(arr);
    }

    public static String createTask(long groupId, String title, String description) throws Exception {
        JsonObject json = new JsonObject();
        json.addProperty("createdById", CurrentUser.getId());
        json.addProperty("title", title);
        json.addProperty("description", description);
        json.addProperty("deadline", (String) null);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/groups/" + groupId + "/tasks"))
                .header("Content-Type", "application/json; charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public static Task updateTaskStatus(long groupId, long taskId, String status) throws Exception {
        JsonObject json = new JsonObject();
        json.addProperty("status", status);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/groups/" + groupId + "/tasks/" + taskId + "/status"))
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        String body = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        return gson.fromJson(body, Task.class);
    }


    // ============================
    //        RESOURCES
    // ============================

    public static List<Resource> getResources(long groupId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/groups/" + groupId + "/resources"))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        String body = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        Resource[] arr = gson.fromJson(body, Resource[].class);

        return Arrays.asList(arr);
    }

    public static void createResourceLink(long groupId, String title, String url) throws Exception {
        JsonObject json = new JsonObject();
        json.addProperty("title", title);
        json.addProperty("url", url);
        json.addProperty("createdById", CurrentUser.getId());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/groups/" + groupId + "/resources"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }


    // 🔥 ЄДИНИЙ ПРАВИЛЬНИЙ МЕТОД ДЛЯ ФАЙЛІВ
    public static void uploadResourceMultipart(long groupId, String title, File file) throws Exception {

        String boundary = "----Boundary" + System.currentTimeMillis();

        String metaJson = "{ \"title\": \"" + title + "\", \"uploadedById\": " + CurrentUser.getId() + " }";

        byte[] fileBytes = Files.readAllBytes(file.toPath());

        String part1 = "--" + boundary + "\r\n"
                + "Content-Disposition: form-data; name=\"meta\"\r\n"
                + "Content-Type: application/json\r\n\r\n"
                + metaJson + "\r\n";

        String part2Header = "--" + boundary + "\r\n"
                + "Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"\r\n"
                + "Content-Type: application/octet-stream\r\n\r\n";

        String ending = "\r\n--" + boundary + "--";

        byte[] body = join(
                part1.getBytes(),
                part2Header.getBytes(),
                fileBytes,
                ending.getBytes()
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/groups/" + groupId + "/resources/upload"))
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .POST(HttpRequest.BodyPublishers.ofByteArray(body))
                .build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }


    // helper
    private static byte[] join(byte[]... arrays) {
        int total = 0;
        for (byte[] arr : arrays) total += arr.length;

        byte[] result = new byte[total];
        int pos = 0;

        for (byte[] arr : arrays) {
            System.arraycopy(arr, 0, result, pos, arr.length);
            pos += arr.length;
        }
        return result;
    }
}
