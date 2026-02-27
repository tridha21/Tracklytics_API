package com.example.demo.controller;

import com.example.demo.entity.Group;
import com.example.demo.service.GroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService service;

    public GroupController(GroupService service) {
        this.service = service;
    }


    @PostMapping
    public Group createGroup(@RequestBody Group group) {
        return service.save(group);
    }


    @GetMapping
    public List<Group> getAllGroups() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Group getGroup(@PathVariable Long id) {
        return service.getAll()
                .stream()
                .filter(g -> g.getGroupId().equals(id))
                .findFirst()
                .orElseThrow();
    }

    @PutMapping("/{id}")
    public Group updateGroup(@PathVariable Long id,
                             @RequestBody Group group) {

        Group existing = getGroup(id);

        existing.setGroupCode(group.getGroupCode());
        existing.setMaxMembers(group.getMaxMembers());
        existing.setGuide(group.getGuide());

        return service.save(existing);
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable Long id) {
        service.getAll().removeIf(g -> g.getGroupId().equals(id));
    }
}

