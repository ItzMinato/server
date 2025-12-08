package com.studyplatform.server.dto;

public class UploadResourceRequest {

    private Long userId;

    public UploadResourceRequest() {}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
