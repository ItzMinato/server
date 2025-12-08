package com.studyplatform.server.service;

import com.studyplatform.server.dto.JoinGroupRequest;
import com.studyplatform.server.entity.Membership;
import com.studyplatform.server.entity.StudyGroup;
import com.studyplatform.server.entity.User;
import com.studyplatform.server.repository.MembershipRepository;
import com.studyplatform.server.repository.StudyGroupRepository;
import com.studyplatform.server.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipService {

    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;
    private final StudyGroupRepository groupRepository;
    private final ActivityLogService activityLogService;

    public MembershipService(
            MembershipRepository membershipRepository,
            UserRepository userRepository,
            StudyGroupRepository groupRepository,
            ActivityLogService activityLogService
    ) {
        this.membershipRepository = membershipRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.activityLogService = activityLogService;
    }

    public Membership joinGroup(Long groupId, JoinGroupRequest req) {

        StudyGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (membershipRepository.existsByUserAndGroup(user, group)) {
            throw new RuntimeException("User already in group");
        }

        Membership membership = new Membership();
        membership.setUser(user);
        membership.setGroup(group);
        membership.setRole(req.getRole() != null ? req.getRole() : "MEMBER");

        Membership saved = membershipRepository.save(membership);

        activityLogService.log(user, "Joined group: " + group.getName());

        return saved;
    }

    public List<Membership> getGroupMembers(Long groupId) {
        StudyGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        return membershipRepository.findByGroup(group);
    }
}
