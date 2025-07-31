package com.example.testapi.testmembers.service;

import com.example.testapi.common.util.IdGenerator;
import com.example.testapi.testmembers.dto.TestMemberDto;
import com.example.testapi.testmembers.entity.TestMember;
import com.example.testapi.testmembers.repository.TestMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestMemberService {
    private final TestMemberRepository testMemberRepository;

    @Transactional
    public TestMemberDto.Response createMember(TestMemberDto.Request request) {
        TestMember testMember = new TestMember();
        testMember.setTestMemberId(IdGenerator.generateId());
        final String testMemberName = request.getTestMemberName();
        testMember.setTestMemberName(testMemberName);

        if (testMemberRepository.existsByTestMemberName(testMemberName)) {
            throw new RuntimeException("동일한 이름의 멤버가 존재합니다. -> 동명이인은 무시됩니다.");
        }

        TestMember savedMember = testMemberRepository.save(testMember);

        TestMemberDto.Response response = new TestMemberDto.Response();
        response.setTestMemberId(savedMember.getTestMemberId());
        response.setTestMemberName(savedMember.getTestMemberName());
        return response;
    }

    public TestMemberDto.Response getMember(Long testMemberId) {
        TestMember testMember = testMemberRepository.findById(testMemberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        TestMemberDto.Response response = new TestMemberDto.Response();
        response.setTestMemberId(testMember.getTestMemberId());
        response.setTestMemberName(testMember.getTestMemberName());
        return response;
    }

    @Transactional
    public TestMemberDto.Response updateMember(Long testMemberId, TestMemberDto.Request request) {
        TestMember testMember = testMemberRepository.findById(testMemberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        testMember.setTestMemberName(request.getTestMemberName());
        TestMember updatedMember = testMemberRepository.save(testMember);

        TestMemberDto.Response response = new TestMemberDto.Response();
        response.setTestMemberId(updatedMember.getTestMemberId());
        response.setTestMemberName(updatedMember.getTestMemberName());
        return response;
    }

    @Transactional
    public void deleteMember(Long testMemberId) {
        if (!testMemberRepository.existsById(testMemberId)) {
            throw new RuntimeException("Member not found");
        }
        testMemberRepository.deleteById(testMemberId);
    }

    public List<TestMemberDto.Response> getAllMembers() {
        return testMemberRepository.findAll()
                .stream()
                .map(testMember -> TestMemberDto.Response.builder()
                        .testMemberId(testMember.getTestMemberId())
                        .testMemberName(testMember.getTestMemberName())
                        .build())
                .collect(Collectors.toList());
    }
}
