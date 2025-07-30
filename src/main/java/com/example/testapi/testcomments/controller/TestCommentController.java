package com.example.testapi.testcomments.controller;

import com.example.testapi.testcomments.dto.TestCommentDto;
import com.example.testapi.testcomments.service.TestCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test-api/test-comments")
@RequiredArgsConstructor
public class TestCommentController {
    private final TestCommentService testCommentService;

    @PutMapping("/{testCommentId}")
    public ResponseEntity<TestCommentDto.Response> updateComment(
            @PathVariable String testCommentId,
            @RequestBody TestCommentDto.UpdateRequest request) {
        return ResponseEntity.ok(testCommentService.updateComment(testCommentId, request));
    }

    @DeleteMapping("/{testCommentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable String testCommentId) {
        testCommentService.deleteComment(testCommentId);
        return ResponseEntity.noContent().build();
    }
}
