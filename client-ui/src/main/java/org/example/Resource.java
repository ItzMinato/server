package org.example;

public class Resource {

    private long id;
    private String title;
    private String url;         // якщо ресурс - це лінк
    private String fileName;    // якщо ресурс - це файл
    private String fileBase64;  // вміст файлу у Base64

    public String getUrl() { return url; }
    public String getFileName() { return fileName; }
    public String getFileBase64() { return fileBase64; }

    @Override
    public String toString() {
        if (fileName != null)
            return title + " (file: " + fileName + ")";
        return title + " (link)";
    }
}