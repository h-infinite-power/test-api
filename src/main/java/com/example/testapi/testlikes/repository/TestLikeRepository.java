package com.example.testapi.testlikes.repository;

import com.example.testapi.testlikes.entity.TestLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestLikeRepository extends JpaRepository<TestLike, String> {
}
