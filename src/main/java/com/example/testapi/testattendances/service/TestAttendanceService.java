package com.example.testapi.testattendances.service;

import com.example.testapi.common.util.IdGenerator;
import com.example.testapi.testattendances.dto.TestAttendanceDetailDto;
import com.example.testapi.testattendances.dto.TestAttendanceDto;
import com.example.testapi.testattendances.dto.TestAttendanceWithCountsDto;
import com.example.testapi.testattendances.entity.TestAttendance;
import com.example.testapi.testattendances.repository.TestAttendanceRepository;
import com.example.testapi.testcomments.entity.TestComment;
import com.example.testapi.testlikes.entity.TestLike;
import com.example.testapi.testmembers.entity.TestMember;
import com.example.testapi.testmembers.repository.TestMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
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

        if (!CollectionUtils.isEmpty(testAttendanceRepository.findByMemberIdAndDate(member.getTestMemberId(), attendance.getTestAttendanceDate()))) {
            throw new RuntimeException("동일한 일자에 두 번 이상 출석체크 할 수 없습니다.");
        }

        TestAttendance savedAttendance = testAttendanceRepository.save(attendance);

        TestAttendanceDto.Response response = new TestAttendanceDto.Response();
        response.setTestAttendanceId(savedAttendance.getTestAttendanceId());
        response.setTestMemberId(savedAttendance.getTestMember().getTestMemberId());
        response.setTestAttendanceDate(savedAttendance.getTestAttendanceDate());
        response.setTestLikesCount(0L);
        response.setTestCommentsCount(0L);
        return response;
    }

    public List<TestAttendanceWithCountsDto> getAllAttendances() {
        return testAttendanceRepository.findAllWithCounts();
    }

    public List<TestAttendanceDto.DetailResponse> getAttendanceDetail(Long testAttendanceId) {
        List<TestAttendance> results = testAttendanceRepository.findByIdWithDetails(testAttendanceId);
        if (results.isEmpty()) {
            throw new RuntimeException("Attendance not found");
        }

        return getDetailResponses(results);
    }

    private static List<TestAttendanceDto.DetailResponse> getDetailResponses(List<TestAttendance> results) {
        List<TestAttendanceDto.DetailResponse> detailResponseList = results.stream().map(testAttendance -> {
                TestAttendanceDto.DetailResponse detailResponse = new TestAttendanceDto.DetailResponse();
                detailResponse.setTestAttendanceId(testAttendance.getTestAttendanceId());
                detailResponse.setTestMemberId(testAttendance.getTestMember().getTestMemberId());
                detailResponse.setTestAttendanceDate(testAttendance.getTestAttendanceDate());

                detailResponse.setTestComments(getComments(testAttendance));
                detailResponse.setTestLikes(getLikes(testAttendance));
                return detailResponse;
            }).collect(Collectors.toList());
        return detailResponseList;
    }

    private static List<TestAttendanceDto.TestLikeInfo> getLikes(TestAttendance testAttendance) {
        return testAttendance.getTestLikes().stream()
            .map(testLike -> {
                TestAttendanceDto.TestLikeInfo likeInfo = new TestAttendanceDto.TestLikeInfo();
                likeInfo.setTestLikeId(testLike.getTestLikeId());
                likeInfo.setTestMemberId(testLike.getTestMember().getTestMemberId());
                likeInfo.setTestMemberName(testLike.getTestMember().getTestMemberName());
                return likeInfo;
            }).collect(Collectors.toList());
    }

    private static List<TestAttendanceDto.TestCommentInfo> getComments(TestAttendance testAttendance) {
        return testAttendance.getTestComments().stream()
            .map(testComment -> {
                TestAttendanceDto.TestCommentInfo commentInfo = new TestAttendanceDto.TestCommentInfo();
                commentInfo.setTestCommentId(testComment.getTestCommentId());
                commentInfo.setTestMemberId(testComment.getTestMember().getTestMemberId());
                commentInfo.setTestMemberName(testComment.getTestMember().getTestMemberName());
                commentInfo.setTestComment(testComment.getTestComment());
                return commentInfo;
            }).collect(Collectors.toList());
    }

    @Transactional
    public void deleteAttendance(Long testAttendanceId) {
        if (!testAttendanceRepository.existsById(testAttendanceId)) {
            throw new RuntimeException("Attendance not found");
        }
        testAttendanceRepository.deleteById(testAttendanceId);
    }
}
