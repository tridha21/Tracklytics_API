package com.example.demo.service;
import com.example.demo.entity.GroupMember;
import com.example.demo.repository.GroupMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupMemberService {

    private final GroupMemberRepository repository;

    public GroupMemberService(GroupMemberRepository repository) {
        this.repository = repository;
    }

    public GroupMember save(GroupMember member) {
        return repository.save(member);
    }

    public List<GroupMember> getAll() {
        return repository.findAll();
    }
}

