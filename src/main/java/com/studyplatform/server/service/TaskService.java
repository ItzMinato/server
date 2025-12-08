package com.studyplatform.server.service;

import com.studyplatform.server.dto.CreateTaskRequest;
import com.studyplatform.server.entity.Task;
import com.studyplatform.server.entity.StudyGroup;
import com.studyplatform.server.entity.User;
import com.studyplatform.server.repository.TaskRepository;
import com.studyplatform.server.repository.StudyGroupRepository;
import com.studyplatform.server.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final StudyGroupRepository groupRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository,
                       StudyGroupRepository groupRepository,
                       UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public Task createTask(Long groupId, CreateTaskRequest req) {
        StudyGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        User creator = userRepository.findById(req.getCreatedById())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = new Task();
        task.setGroup(group);
        task.setCreatedBy(creator);
        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());

        if (req.getDeadline() != null) {
            task.setDeadline(LocalDateTime.parse(req.getDeadline()));
        }

        return taskRepository.save(task);
    }

    public List<Task> getTasks(Long groupId) {
        StudyGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        return taskRepository.findByGroup(group);
    }

    // ✅ ПРАВИЛЬНО: метод ВНУТРІШЕ класу
    public Task updateStatus(Long taskId, String newStatus) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!newStatus.equals("OPEN") &&
                !newStatus.equals("IN_PROGRESS") &&
                !newStatus.equals("DONE")) {
            throw new RuntimeException("Invalid status");
        }

        task.setStatus(newStatus);
        return taskRepository.save(task);
    }
}
