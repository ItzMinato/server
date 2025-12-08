package com.studyplatform.server.repository;

import com.studyplatform.server.entity.StudyGroup;
import com.studyplatform.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {

    // Повертає всі групи, створені певним юзером (teacher)
    List<StudyGroup> findByCreatedBy(User createdBy);
}
