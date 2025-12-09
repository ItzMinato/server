package com.studyplatform.server.service;

import com.studyplatform.server.dto.UploadResourceRequest;
import com.studyplatform.server.entity.ResourceFile;
import com.studyplatform.server.entity.StudyGroup;
import com.studyplatform.server.entity.User;
import com.studyplatform.server.repository.ResourceFileRepository;
import com.studyplatform.server.repository.StudyGroupRepository;
import com.studyplatform.server.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ResourceService {

    private final ResourceFileRepository resourceRepository;
    private final StudyGroupRepository groupRepository;
    private final UserRepository userRepository;
    private final ActivityLogService activityLogService;

    public ResourceService(
            ResourceFileRepository resourceRepository,
            StudyGroupRepository groupRepository,
            UserRepository userRepository,
            ActivityLogService activityLogService
    ) {
        this.resourceRepository = resourceRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.activityLogService = activityLogService;
    }

    public ResourceFile uploadFile(Long groupId, UploadResourceRequest req, MultipartFile file)
            throws IOException {

        StudyGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        User uploader = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String uploadDir = System.getProperty("user.dir") + "/uploads/" + groupId;
        new File(uploadDir).mkdirs();

        String filePath = uploadDir + "/" + file.getOriginalFilename();
        file.transferTo(new File(filePath));

        ResourceFile resource = new ResourceFile();
        resource.setGroup(group);
        resource.setUploadedBy(uploader);
        resource.setFileName(file.getOriginalFilename());
        resource.setFilePath(filePath);

        ResourceFile saved = resourceRepository.save(resource);

        activityLogService.log(uploader, "Uploaded file: " + file.getOriginalFilename());

        return saved;
    }

    public List<ResourceFile> getFiles(Long groupId) {
        StudyGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        return resourceRepository.findByGroup(group);
    }

    public ResourceFile getFile(Long fileId) {
        return resourceRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));
    }

    public void deleteFile(Long groupId, Long fileId) {

        ResourceFile file = resourceRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        if (!file.getGroup().getId().equals(groupId)) {
            throw new RuntimeException("File does not belong to this group");
        }

        File diskFile = new File(file.getFilePath());
        if (diskFile.exists()) diskFile.delete();

        resourceRepository.delete(file);
    }
}
