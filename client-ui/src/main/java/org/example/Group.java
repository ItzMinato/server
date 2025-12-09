package org.example;

public class Group {

    private Long id;
    private String name;

    public Group() {} // Gson needs empty constructor

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
