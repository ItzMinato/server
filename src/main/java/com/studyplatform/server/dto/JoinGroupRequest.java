package com.studyplatform.server.dto;

public class JoinGroupRequest {
    private Long userId;
    private String role;

    public JoinGroupRequest() {}

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
