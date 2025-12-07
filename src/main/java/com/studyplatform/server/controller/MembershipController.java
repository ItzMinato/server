package com.studyplatform.server.controller;

import com.studyplatform.server.dto.JoinGroupRequest;
import com.studyplatform.server.entity.Membership;
import com.studyplatform.server.service.MembershipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups/{groupId}/members")
public class MembershipController {

    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @PostMapping
    public ResponseEntity<Membership> joinGroup(
            @PathVariable Long groupId,
            @RequestBody JoinGroupRequest req
    ) {
        Membership m = membershipService.joinGroup(groupId, req);
        return ResponseEntity.ok(m);
    }

    @GetMapping
    public ResponseEntity<List<Membership>> getMembers(@PathVariable Long groupId) {
        return ResponseEntity.ok(membershipService.getGroupMembers(groupId));
    }
}
