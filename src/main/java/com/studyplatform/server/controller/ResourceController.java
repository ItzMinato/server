package com.studyplatform.server.controller;

import com.studyplatform.server.dto.UploadResourceRequest;
import com.studyplatform.server.entity.ResourceFile;
import com.studyplatform.server.service.ResourceService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/api/groups/{groupId}/resources")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }
    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ResourceFile> upload(
            @PathVariable Long groupId,
            @RequestPart("meta") UploadResourceRequest req,
            @RequestPart("file") MultipartFile file
    ) throws IOException {

        return ResponseEntity.ok(resourceService.uploadFile(groupId, req, file));
    }
    @GetMapping
    public ResponseEntity<List<ResourceFile>> getFiles(@PathVariable Long groupId) {
        return ResponseEntity.ok(resourceService.getFiles(groupId));
    }

    @GetMapping("/{fileId}/download")
    public ResponseEntity<byte[]> downloadFile(
            @PathVariable Long groupId,
            @PathVariable Long fileId
    ) throws IOException {

        ResourceFile file = resourceService.getFile(fileId);

        File diskFile = new File(file.getFilePath());
        if (!diskFile.exists()) {
            return ResponseEntity.notFound().build();
        }

        byte[] fileBytes = Files.readAllBytes(diskFile.toPath());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFileName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileBytes);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteFile(
            @PathVariable Long groupId,
            @PathVariable Long fileId
    ) {
        resourceService.deleteFile(groupId, fileId);
        return ResponseEntity.noContent().build();
    }
}
