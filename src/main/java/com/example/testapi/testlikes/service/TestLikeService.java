package com.example.testapi.testlikes.service;

import com.example.testapi.common.util.IdGenerator;
import com.example.testapi.testlikes.dto.TestLikeDto;
import com.example.testapi.testlikes.entity.TestLike;
import com.example.testapi.testlikes.repository.TestLikeRepository;
import com.example.testapi.testattendances.entity.TestAttendance;
import com.example.testapi.testattendances.repository.TestAttendanceRepository;
import com.example.testapi.testmembers.entity.TestMember;
import com.example.testapi.testmembers.repository.TestMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestLikeService {
    private final TestLikeRepository testLikeRepository;
    private final TestAttendanceRepository testAttendanceRepository;
    private final TestMemberRepository testMemberRepository;

    @Transactional
    public TestLikeDto.Response createLike(String testAttendanceId, TestLikeDto.Request request) {
        TestAttendance attendance = testAttendanceRepository.findById(testAttendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        TestMember member = testMemberRepository.findById(request.getTestMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        TestLike testLike = new TestLike();
        testLike.setTestLikeId(IdGenerator.generateId("LIK"));
        testLike.setTestAttendance(attendance);
        testLike.setTestMember(member);

        TestLike savedLike = testLikeRepository.save(testLike);

        TestLikeDto.Response response = new TestLikeDto.Response();
        response.setTestLikeId(savedLike.getTestLikeId());
        return response;
    }

    @Transactional
    public void deleteLike(String testLikeId) {
        if (!testLikeRepository.existsById(testLikeId)) {
            throw new RuntimeException("Like not found");
        }
        testLikeRepository.deleteById(testLikeId);
    }
}
