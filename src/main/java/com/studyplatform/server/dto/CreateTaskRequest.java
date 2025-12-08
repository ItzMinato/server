package com.studyplatform.server.dto;

public class CreateTaskRequest {

    private Long createdById;
    private String title;
    private String description;
    private String deadline;

    public CreateTaskRequest() {}

    public Long getCreatedById() { return createdById; }
    public void setCreatedById(Long createdById) { this.createdById = createdById; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDeadline() { return deadline; }
    public void setDeadline(String deadline) { this.deadline = deadline; }
}
