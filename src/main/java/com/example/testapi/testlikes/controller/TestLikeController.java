package com.example.testapi.testlikes.controller;

import com.example.testapi.testlikes.service.TestLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test-api/test-likes")
@RequiredArgsConstructor
public class TestLikeController {
    private final TestLikeService testLikeService;

    @DeleteMapping("/{testLikeId}")
    public ResponseEntity<Void> deleteLike(@PathVariable String testLikeId) {
        testLikeService.deleteLike(testLikeId);
        return ResponseEntity.noContent().build();
    }
}
