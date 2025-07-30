package com.example.testapi.testmembers.controller;

import com.example.testapi.testmembers.dto.TestMemberDto;
import com.example.testapi.testmembers.service.TestMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test-api/test-members")
@RequiredArgsConstructor
public class TestMemberController {
    private final TestMemberService testMemberService;

    @PostMapping
    public ResponseEntity<TestMemberDto.Response> createMember(@RequestBody TestMemberDto.Request request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(testMemberService.createMember(request));
    }

    @GetMapping("/{testMemberId}")
    public ResponseEntity<TestMemberDto.Response> getMember(@PathVariable String testMemberId) {
        return ResponseEntity.ok(testMemberService.getMember(testMemberId));
    }

    @PutMapping("/{testMemberId}")
    public ResponseEntity<TestMemberDto.Response> updateMember(
            @PathVariable String testMemberId,
            @RequestBody TestMemberDto.Request request) {
        return ResponseEntity.ok(testMemberService.updateMember(testMemberId, request));
    }

    @DeleteMapping("/{testMemberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable String testMemberId) {
        testMemberService.deleteMember(testMemberId);
        return ResponseEntity.noContent().build();
    }
}
