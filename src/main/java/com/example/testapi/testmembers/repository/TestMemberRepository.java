package com.example.testapi.testmembers.repository;

import com.example.testapi.testmembers.entity.TestMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestMemberRepository extends JpaRepository<TestMember, String> {
}
