package com.studyplatform.server.dto;

public class StudyGroupResponse {

    private Long id;
    private String name;
    private String description;
    private Long createdById;
    private String createdAt;

    public StudyGroupResponse() {}

    public StudyGroupResponse(Long id, String name, String description,
                              Long createdById, String createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdById = createdById;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getCreatedById() { return createdById; }
    public void setCreatedById(Long createdById) { this.createdById = createdById; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
