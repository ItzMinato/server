package org.example;

public class CurrentUser {

    private static Long userId;
    private static String username;

    public static void setUserId(Long id) {
        userId = id;
    }

    public static Long getUserId() {
        return userId;
    }

    public static void setUsername(String name) {
        username = name;
    }

    public static String getUsername() {
        return username;
    }

    public static void clear() {
        userId = null;
        username = null;
    }
}
