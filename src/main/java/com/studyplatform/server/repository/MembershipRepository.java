package com.studyplatform.server.repository;

import com.studyplatform.server.entity.Membership;
import com.studyplatform.server.entity.StudyGroup;
import com.studyplatform.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {

    List<Membership> findByGroup(StudyGroup group);

    List<Membership> findByUser(User user);

    boolean existsByUserAndGroup(User user, StudyGroup group);
}
