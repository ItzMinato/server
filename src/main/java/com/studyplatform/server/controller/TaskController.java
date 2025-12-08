package com.studyplatform.server.controller;

import com.studyplatform.server.dto.CreateTaskRequest;
import com.studyplatform.server.dto.UpdateTaskStatusRequest;
import com.studyplatform.server.entity.Task;
import com.studyplatform.server.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups/{groupId}/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(
            @PathVariable Long groupId,
            @RequestBody CreateTaskRequest req
    ) {
        return ResponseEntity.ok(taskService.createTask(groupId, req));
    }

    @GetMapping
    public ResponseEntity<List<Task>> getTasks(@PathVariable Long groupId) {
        return ResponseEntity.ok(taskService.getTasks(groupId));
    }

    // ✅ Правильне місце — всередині класу!
    @PatchMapping("/{taskId}/status")
    public ResponseEntity<Task> updateStatus(
            @PathVariable Long groupId,
            @PathVariable Long taskId,
            @RequestBody UpdateTaskStatusRequest req
    ) {
        return ResponseEntity.ok(taskService.updateStatus(taskId, req.getStatus()));
    }
}
