package com.studyplatform.server.service;

import com.studyplatform.server.dto.CreateGroupRequest;
import com.studyplatform.server.dto.StudyGroupResponse;
import com.studyplatform.server.entity.StudyGroup;
import com.studyplatform.server.entity.User;
import com.studyplatform.server.repository.StudyGroupRepository;
import com.studyplatform.server.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudyGroupService {

    private final StudyGroupRepository groupRepository;
    private final UserRepository userRepository;
    private final ActivityLogService activityLogService;

    public StudyGroupService(
            StudyGroupRepository groupRepository,
            UserRepository userRepository,
            ActivityLogService activityLogService
    ) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.activityLogService = activityLogService;
    }

    public StudyGroupResponse createGroup(CreateGroupRequest request) {

        User creator = userRepository.findById(request.getCreatorId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (creator.getRole() != User.Role.TEACHER) {
            throw new RuntimeException("Only a TEACHER can create groups!");
        }

        StudyGroup group = new StudyGroup();
        group.setName(request.getName());
        group.setDescription(request.getDescription());
        group.setCreatedBy(creator);

        StudyGroup saved = groupRepository.save(group);

        activityLogService.log(creator, "Created group: " + saved.getName());

        return toResponse(saved);
    }

    public List<StudyGroupResponse> getAllGroups() {
        return groupRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<StudyGroupResponse> getTeacherGroups(Long teacherId) {

        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return groupRepository.findByCreatedBy(teacher)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private StudyGroupResponse toResponse(StudyGroup g) {
        return new StudyGroupResponse(
                g.getId(),
                g.getName(),
                g.getDescription(),
                g.getCreatedBy().getId(),
                g.getCreatedAt().toString()
        );
    }
}
