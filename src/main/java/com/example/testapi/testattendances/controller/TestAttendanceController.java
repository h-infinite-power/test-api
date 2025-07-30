package com.example.testapi.testattendances.controller;

import com.example.testapi.testattendances.dto.TestAttendanceDto;
import com.example.testapi.testattendances.service.TestAttendanceService;
import com.example.testapi.testlikes.dto.TestLikeDto;
import com.example.testapi.testlikes.service.TestLikeService;
import com.example.testapi.testcomments.dto.TestCommentDto;
import com.example.testapi.testcomments.service.TestCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test-api/test-attendances")
@RequiredArgsConstructor
public class TestAttendanceController {
    private final TestAttendanceService testAttendanceService;
    private final TestLikeService testLikeService;
    private final TestCommentService testCommentService;

    @PostMapping
    public ResponseEntity<TestAttendanceDto.Response> createAttendance(@RequestBody TestAttendanceDto.Request request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(testAttendanceService.createAttendance(request));
    }

    @GetMapping
    public ResponseEntity<List<TestAttendanceDto.Response>> getAllAttendances() {
        return ResponseEntity.ok(testAttendanceService.getAllAttendances());
    }

    @GetMapping("/{testAttendanceId}")
    public ResponseEntity<TestAttendanceDto.DetailResponse> getAttendanceDetail(@PathVariable String testAttendanceId) {
        return ResponseEntity.ok(testAttendanceService.getAttendanceDetail(testAttendanceId));
    }

    @DeleteMapping("/{testAttendanceId}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable String testAttendanceId) {
        testAttendanceService.deleteAttendance(testAttendanceId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{testAttendanceId}/test-likes")
    public ResponseEntity<TestLikeDto.Response> addLike(
            @PathVariable String testAttendanceId,
            @RequestBody TestLikeDto.Request request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(testLikeService.createLike(testAttendanceId, request));
    }

    @PostMapping("/{testAttendanceId}/test-comments")
    public ResponseEntity<TestCommentDto.Response> addComment(
            @PathVariable String testAttendanceId,
            @RequestBody TestCommentDto.Request request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(testCommentService.createComment(testAttendanceId, request));
    }
}
