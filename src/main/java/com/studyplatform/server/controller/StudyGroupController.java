package com.studyplatform.server.controller;

import com.studyplatform.server.dto.CreateGroupRequest;
import com.studyplatform.server.dto.StudyGroupResponse;
import com.studyplatform.server.service.StudyGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class StudyGroupController {

    private final StudyGroupService groupService;

    public StudyGroupController(StudyGroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public ResponseEntity<StudyGroupResponse> createGroup(@RequestBody CreateGroupRequest request) {
        return ResponseEntity.ok(groupService.createGroup(request));
    }

    @GetMapping
    public ResponseEntity<List<StudyGroupResponse>> getGroups() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    @GetMapping("/teacher/{id}")
    public ResponseEntity<List<StudyGroupResponse>> getTeacherGroups(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.getTeacherGroups(id));
    }
}
