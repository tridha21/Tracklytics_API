package com.example.demo.controller;

import com.example.demo.entity.Group;
import com.example.demo.entity.Project;
import com.example.demo.entity.User;
import com.example.demo.enums.ProjectStatus;
import com.example.demo.service.ProjectService;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public Project createProject(@RequestBody Project project) {

        project.setUploadAt(LocalDateTime.now());

        if (project.getStatus() == null) {
            project.setStatus(ProjectStatus.PENDING);
        }

        return projectService.save(project);
    }

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAll();
    }

    @GetMapping("/{projectId}")
    public Project getProjectById(@PathVariable Long projectId) {

        return projectService.getAll()
                .stream()
                .filter(p -> p.getProjectId().equals(projectId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @GetMapping("/group/{groupId}")
    public List<Project> getProjectsByGroup(@PathVariable Long groupId) {

        return projectService.getAll()
                .stream()
                .filter(p -> p.getGroup().getGroupId().equals(groupId))
                .toList();
    }

    @GetMapping("/student/{studentId}")
    public List<Project> getProjectsByStudent(@PathVariable Long studentId) {

        return projectService.getAll()
                .stream()
                .filter(p -> p.getStudent().getUserId().equals(studentId))
                .toList();
    }

    @PutMapping("/{projectId}/approve")
    public Project approveProject(@PathVariable Long projectId) {

        Project project = getProjectById(projectId);

        project.setStatus(ProjectStatus.APPROVED);

        return projectService.save(project);
    }

    @PutMapping("/{projectId}/reject")
    public Project rejectProject(@PathVariable Long projectId) {

        Project project = getProjectById(projectId);

        project.setStatus(ProjectStatus.REJECTED);

        return projectService.save(project);
    }

    @PutMapping("/{projectId}")
    public Project updateProject(@PathVariable Long projectId,
                                 @RequestBody Project updatedProject) {

        Project existing = getProjectById(projectId);

        existing.setTitle(updatedProject.getTitle());
        existing.setDescription(updatedProject.getDescription());
        existing.setFileLink(updatedProject.getFileLink());
        existing.setStatus(updatedProject.getStatus());

        return projectService.save(existing);
    }

    @DeleteMapping("/{projectId}")
    public String deleteProject(@PathVariable Long projectId) {

        projectService.delete(projectId);

        return "Project deleted successfully";
    }

}
