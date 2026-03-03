package com.example.demo.service;
import com.example.demo.entity.Group;
import com.example.demo.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Group save(Group group) {
        return groupRepository.save(group);
    }

    public List<Group> getAll() {
        return groupRepository.findAll();
    }
    public Optional<Group> findByGroupCode(String code) {
        return groupRepository.findByGroupCode(code);
    }
    public void delete(long id) {
        groupRepository.deleteById(id);
    }
}

