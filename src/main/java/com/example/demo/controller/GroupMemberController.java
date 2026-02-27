package com.example.demo.controller;

import com.example.demo.entity.GroupMember;
import com.example.demo.entity.GroupMemberId;
import com.example.demo.service.GroupMemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group-members")
public class GroupMemberController {

    private final GroupMemberService service;

    public GroupMemberController(GroupMemberService service) {
        this.service = service;
    }

    @PostMapping
    public GroupMember addMember(@RequestBody GroupMember member) {
        return service.save(member);
    }

    @GetMapping
    public List<GroupMember> getAllMembers() {
        return service.getAll();
    }

    @GetMapping("/group/{groupId}")
    public List<GroupMember> getByGroup(@PathVariable Long groupId) {
        return service.getAll()
                .stream()
                .filter(m -> m.getGroup().getGroupId().equals(groupId))
                .toList();
    }

    @GetMapping("/student/{userId}")
    public List<GroupMember> getByStudent(@PathVariable Long userId) {
        return service.getAll()
                .stream()
                .filter(m -> m.getUser().getUserId().equals(userId))
                .toList();
    }

    @DeleteMapping("/{groupId}/{userId}")
    public void deleteMember(@PathVariable Long groupId,
                             @PathVariable Long userId) {

        GroupMemberId id = new GroupMemberId(groupId, userId);

        service.getAll().removeIf(m -> m.getId().equals(id));
    }
}

