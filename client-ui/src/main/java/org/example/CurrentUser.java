package org.example;

public class CurrentUser {

    private static long id;
    private static String username;
    private static String role;

    public static void set(long userId, String user, String userRole) {
        id = userId;
        username = user;
        role = userRole;
    }

    public static long getId() { return id; }
    public static String getUsername() { return username; }
    public static String getRole() { return role; }

    public static void clear() {
        id = 0;
        username = null;
        role = null;
    }
}
