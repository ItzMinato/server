package org.example;

public class CurrentUser {

    private static String username;
    private static Long userId;

    public static void setUser(String name, Long id) {
        username = name;
        userId = id;
    }

    public static String getUsername() {
        return username;
    }

    public static Long getUserId() {
        return userId;
    }

    public static void clear() {
        username = null;
        userId = null;
    }
}
