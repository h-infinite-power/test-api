package com.example.testapi.testmembers.service;

import com.example.testapi.common.util.IdGenerator;
import com.example.testapi.testmembers.dto.TestMemberDto;
import com.example.testapi.testmembers.entity.TestMember;
import com.example.testapi.testmembers.repository.TestMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestMemberService {
    private final TestMemberRepository testMemberRepository;

    @Transactional
    public TestMemberDto.Response createMember(TestMemberDto.Request request) {
        TestMember testMember = new TestMember();
        testMember.setTestMemberId(IdGenerator.generateId("MEM"));
        testMember.setTestMemberName(request.getTestMemberName());
        
        TestMember savedMember = testMemberRepository.save(testMember);
        
        TestMemberDto.Response response = new TestMemberDto.Response();
        response.setTestMemberId(savedMember.getTestMemberId());
        response.setTestMemberName(savedMember.getTestMemberName());
        return response;
    }

    public TestMemberDto.Response getMember(String testMemberId) {
        TestMember testMember = testMemberRepository.findById(testMemberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        TestMemberDto.Response response = new TestMemberDto.Response();
        response.setTestMemberId(testMember.getTestMemberId());
        response.setTestMemberName(testMember.getTestMemberName());
        return response;
    }

    @Transactional
    public TestMemberDto.Response updateMember(String testMemberId, TestMemberDto.Request request) {
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
    public void deleteMember(String testMemberId) {
        if (!testMemberRepository.existsById(testMemberId)) {
            throw new RuntimeException("Member not found");
        }
        testMemberRepository.deleteById(testMemberId);
    }
}
