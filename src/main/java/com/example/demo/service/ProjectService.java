package com.example.demo.service;

import com.example.demo.entity.Group;
import com.example.demo.entity.Project;
import com.example.demo.entity.User;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository,
                          GroupRepository groupRepository,
                          UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public Project save(Project project) {

        Long groupId = project.getGroup().getGroupId();

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        Long userId = project.getStudent().getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        project.setGroup(group);
        project.setStudent(user);

        return projectRepository.save(project);
    }
    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

}


