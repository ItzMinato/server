package com.studyplatform.server.controller;

import com.studyplatform.server.entity.ActivityLog;
import com.studyplatform.server.entity.User;
import com.studyplatform.server.service.ActivityLogService;
import com.studyplatform.server.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity")
public class ActivityLogController {

    private final ActivityLogService logService;
    private final UserService userService;

    public ActivityLogController(ActivityLogService logService, UserService userService) {
        this.logService = logService;
        this.userService = userService;
    }

    @GetMapping("/latest")
    public List<ActivityLog> getLatestLogs() {
        return logService.getLatest();
    }

    @GetMapping("/user/{username}")
    public List<ActivityLog> getUserLogs(@PathVariable String username) {

        User user = userService.getUserEntity(username);

        return logService.getUserActivity(user);
    }
}
