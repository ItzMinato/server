package org.example;

public class Group {

    private Long id;
    private String name;
    private String description;
    private Long createdById;
    private String createdAt;

    public Group() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getCreatedBy() {
        return createdById;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return name;
    }
}
