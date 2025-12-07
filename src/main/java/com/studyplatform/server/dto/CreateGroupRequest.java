package com.studyplatform.server.dto;

public class CreateGroupRequest {
    private String name;
    private String description;
    private Long creatorId;

    public CreateGroupRequest() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getCreatorId() { return creatorId; }
    public void setCreatorId(Long creatorId) { this.creatorId = creatorId; }
}
