package org.example;

public class Task {

    private long id;
    private String title;
    private String description;
    private String status;
    private String deadline;
    private long groupId;

    public Task() {}

    public long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public String getDeadline() { return deadline; }
    public long getGroupId() { return groupId; }

    @Override
    public String toString() {
        return title; // або title + " (" + status + ")"
    }
}
