package com.example.testapi.testcomments.repository;

import com.example.testapi.testcomments.entity.TestComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCommentRepository extends JpaRepository<TestComment, String> {
}
