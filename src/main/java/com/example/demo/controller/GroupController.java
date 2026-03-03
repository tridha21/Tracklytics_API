package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.enums.ProjectStatus;
import com.example.demo.service.GroupMemberService;
import com.example.demo.service.GroupService;

import com.example.demo.service.ProjectService;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/groups")
@CrossOrigin(origins = "http://127.0.0.1:3000")
public class GroupController {

    private final GroupService service;
    private final ProjectService projectService;
    private final GroupMemberService groupMemberService;



    public GroupController(GroupService service, ProjectService projectService, GroupMemberService groupMemberService) {
        this.service = service;
        this.projectService = projectService;
        this.groupMemberService = groupMemberService;
    }

    // =========================
    // CREATE GROUP
    // =========================
    @PostMapping("/create")
    public Group createGroup(@RequestParam Long studentId,
                             @RequestParam int maxMembers,
                             @RequestParam String title) {

        // 1️⃣ Create group
        Group group = new Group();
        group.setGroupCode(generateCode());
        group.setMaxMembers(maxMembers);

        User student = new User();
        student.setUserId(studentId);

        group.setCreatedBy(student);

        Group savedGroup = service.save(group);

        // 2️⃣ Add creator as group member
        GroupMember member = new GroupMember();

        GroupMemberId id = new GroupMemberId(
                savedGroup.getGroupId(),
                student.getUserId()
        );

        member.setId(id);
        member.setGroup(savedGroup);
        member.setUser(student);

        groupMemberService.save(member);

        // 3️⃣ Create project
        Project project = new Project();
        project.setTitle(title);
        project.setStudent(student);
        project.setGroup(savedGroup);
        project.setStatus(ProjectStatus.PENDING);
        project.setUploadAt(LocalDateTime.now());

        projectService.save(project);

        return savedGroup;
    }

    // =========================
    // JOIN GROUP
    // =========================
    @PostMapping("/join")
    public String joinGroup(@RequestParam Long studentId,
                            @RequestParam String groupCode) {

        Optional<Group> optionalGroup = service.findByGroupCode(groupCode);

        if (optionalGroup.isEmpty()) {
            throw new RuntimeException("Invalid Group Code");
        }

        Group group = optionalGroup.get();

        // Capacity check (if you maintain members list)
        if (group.getMaxMembers() <= 0) {
            throw new RuntimeException("Group is full");
        }

        // Reduce available slot (basic version)
        group.setMaxMembers(group.getMaxMembers() - 1);

        service.save(group);

        return "Joined Successfully";
    }

    // =========================
    // GET ALL
    // =========================
    @GetMapping
    public List<Group> getAllGroups() {
        return service.getAll();
    }

    // =========================
    // GENERATE 6 LETTER CODE
    // =========================
    private String generateCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            code.append(chars.charAt(random.nextInt(chars.length())));
        }

        return code.toString();
    }

    @GetMapping("/{id}")
    public Group getGroup(@PathVariable Long id) {
        return service.getAll()
                .stream()
                .filter(g -> g.getGroupId().equals(id))
                .findFirst()
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable Long id) {
        service.delete(id);
    }
}