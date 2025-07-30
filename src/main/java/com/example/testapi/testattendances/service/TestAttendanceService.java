package com.example.testapi.testattendances.service;

import com.example.testapi.common.util.IdGenerator;
import com.example.testapi.testattendances.dto.TestAttendanceDto;
import com.example.testapi.testattendances.entity.TestAttendance;
import com.example.testapi.testattendances.repository.TestAttendanceRepository;
import com.example.testapi.testmembers.entity.TestMember;
import com.example.testapi.testmembers.repository.TestMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestAttendanceService {
    private final TestAttendanceRepository testAttendanceRepository;
    private final TestMemberRepository testMemberRepository;

    @Transactional
    public TestAttendanceDto.Response createAttendance(TestAttendanceDto.Request request) {
        TestMember member = testMemberRepository.findById(request.getTestMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        TestAttendance attendance = new TestAttendance();
        attendance.setTestAttendanceId(IdGenerator.generateId());
        attendance.setTestMember(member);
        attendance.setTestAttendanceDate(LocalDate.now());

        TestAttendance savedAttendance = testAttendanceRepository.save(attendance);

        TestAttendanceDto.Response response = new TestAttendanceDto.Response();
        response.setTestAttendanceId(savedAttendance.getTestAttendanceId());
        response.setTestMemberId(savedAttendance.getTestMember().getTestMemberId());
        response.setTestAttendanceDate(savedAttendance.getTestAttendanceDate());
        response.setTestLikesCount(0);
        response.setTestCommentsCount(0);
        return response;
    }

    public List<TestAttendanceDto.Response> getAllAttendances() {
        List<Object[]> results = testAttendanceRepository.findAllWithCounts();
        return results.stream()
                .map(result -> {
                    TestAttendanceDto.Response response = new TestAttendanceDto.Response();
                    TestAttendance attendance = (TestAttendance) result[0];
                    response.setTestAttendanceId(attendance.getTestAttendanceId());
                    response.setTestMemberId(attendance.getTestMember().getTestMemberId());
                    response.setTestAttendanceDate(attendance.getTestAttendanceDate());
                    response.setTestLikesCount(((Number) result[1]).intValue());
                    response.setTestCommentsCount(((Number) result[2]).intValue());
                    return response;
                })
                .collect(Collectors.toList());
    }

    public TestAttendanceDto.DetailResponse getAttendanceDetail(String testAttendanceId) {
        List<Object[]> results = testAttendanceRepository.findByIdWithDetails(testAttendanceId);
        if (results.isEmpty()) {
            throw new RuntimeException("Attendance not found");
        }

        TestAttendanceDto.DetailResponse response = new TestAttendanceDto.DetailResponse();
        TestAttendance attendance = (TestAttendance) results.get(0)[0];
        response.setTestAttendanceId(attendance.getTestAttendanceId());
        response.setTestMemberId(attendance.getTestMember().getTestMemberId());
        response.setTestAttendanceDate(attendance.getTestAttendanceDate());

        // Process likes and comments
        List<TestAttendanceDto.TestLikeInfo> likes = results.stream()
                .filter(result -> result[1] != null) // testLikeId not null
                .map(result -> {
                    TestAttendanceDto.TestLikeInfo likeInfo = new TestAttendanceDto.TestLikeInfo();
                    likeInfo.setTestLikeId((long) result[1]);
                    likeInfo.setTestMemberId((long) result[2]);
                    likeInfo.setTestMemberName((String) result[3]);
                    return likeInfo;
                })
                .collect(Collectors.toList());

        List<TestAttendanceDto.TestCommentInfo> comments = results.stream()
                .filter(result -> result[4] != null) // testCommentId not null
                .map(result -> {
                    TestAttendanceDto.TestCommentInfo commentInfo = new TestAttendanceDto.TestCommentInfo();
                    commentInfo.setTestCommentId((long) result[4]);
                    commentInfo.setTestMemberId((long) result[5]);
                    commentInfo.setTestMemberName((String) result[6]);
                    commentInfo.setTestComment((String) result[7]);
                    return commentInfo;
                })
                .collect(Collectors.toList());

        response.setTestLikes(likes);
        response.setTestComments(comments);
        return response;
    }

    @Transactional
    public void deleteAttendance(Long testAttendanceId) {
        if (!testAttendanceRepository.existsById(testAttendanceId)) {
            throw new RuntimeException("Attendance not found");
        }
        testAttendanceRepository.deleteById(testAttendanceId);
    }
}
