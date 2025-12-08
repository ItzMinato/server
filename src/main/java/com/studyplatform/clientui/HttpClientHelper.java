package com.studyplatform.clientui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpClientHelper {

    public static String sendPostRequest(String urlString, String jsonBody) {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            // Надсилаємо тіло запиту
            try (OutputStream os = connection.getOutputStream()) {
                byte[] bytes = jsonBody.getBytes(StandardCharsets.UTF_8);
                os.write(bytes);
            }

            int status = connection.getResponseCode();
            InputStream is = (status >= 200 && status < 300)
                    ? connection.getInputStream()
                    : connection.getErrorStream();

            // Читаємо відповідь сервера
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(is, StandardCharsets.UTF_8))) {

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                return sb.toString();
            }

        } catch (IOException e) {
            return "ERROR: " + e.getMessage();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
