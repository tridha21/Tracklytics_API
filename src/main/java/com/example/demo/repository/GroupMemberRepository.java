package com.example.demo.repository;


import com.example.demo.entity.GroupMember;
import com.example.demo.entity.GroupMemberId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {

}

