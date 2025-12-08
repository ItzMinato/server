package com.studyplatform.server.service;

import com.studyplatform.server.entity.ActivityLog;
import com.studyplatform.server.entity.User;
import com.studyplatform.server.repository.ActivityLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityLogService {

    private final ActivityLogRepository logRepository;

    public ActivityLogService(ActivityLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void log(User user, String action) {
        ActivityLog log = new ActivityLog();
        log.setUser(user);
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now());
        logRepository.save(log);
    }

    public List<ActivityLog> getUserActivity(User user) {
        return logRepository.findByUser(user);
    }

    public List<ActivityLog> getLatest() {
        return logRepository.findTop20ByOrderByTimestampDesc();
    }
}
