package com.studyplatform.server.repository;

import com.studyplatform.server.entity.ResourceFile;
import com.studyplatform.server.entity.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceFileRepository extends JpaRepository<ResourceFile, Long> {

    List<ResourceFile> findByGroup(StudyGroup group);
}
