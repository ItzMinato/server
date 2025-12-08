package com.studyplatform.server.repository;

import com.studyplatform.server.entity.Task;
import com.studyplatform.server.entity.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByGroup(StudyGroup group);
}
