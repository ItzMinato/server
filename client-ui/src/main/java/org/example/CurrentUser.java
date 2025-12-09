package org.example;

public class CurrentUser {

    private static String username;

    public static void setUsername(String name) {
        username = name;
    }

    public static String getUsername() {
        return username;
    }

    public static void clear() {
        username = null;
    }
}
