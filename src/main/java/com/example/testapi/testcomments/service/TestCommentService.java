package com.example.testapi.testcomments.service;

import com.example.testapi.common.util.IdGenerator;
import com.example.testapi.testcomments.dto.TestCommentDto;
import com.example.testapi.testcomments.entity.TestComment;
import com.example.testapi.testcomments.repository.TestCommentRepository;
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
public class TestCommentService {
    private final TestCommentRepository testCommentRepository;
    private final TestAttendanceRepository testAttendanceRepository;
    private final TestMemberRepository testMemberRepository;

    @Transactional
    public TestCommentDto.Response createComment(Long testAttendanceId, TestCommentDto.Request request) {
        TestAttendance attendance = testAttendanceRepository.findById(testAttendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        TestMember member = testMemberRepository.findById(request.getTestMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        TestComment comment = new TestComment();
        comment.setTestCommentId(IdGenerator.generateId());
        comment.setTestAttendance(attendance);
        comment.setTestMember(member);
        comment.setTestComment(request.getTestComment());

        TestComment savedComment = testCommentRepository.save(comment);

        TestCommentDto.Response response = new TestCommentDto.Response();
        response.setTestCommentId(savedComment.getTestCommentId());
        return response;
    }

    @Transactional
    public TestCommentDto.Response updateComment(Long testCommentId, TestCommentDto.UpdateRequest request) {
        TestComment comment = testCommentRepository.findById(testCommentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        comment.setTestComment(request.getTestComment());
        TestComment updatedComment = testCommentRepository.save(comment);

        TestCommentDto.Response response = new TestCommentDto.Response();
        response.setTestCommentId(updatedComment.getTestCommentId());
        return response;
    }

    @Transactional
    public void deleteComment(Long testCommentId) {
        final Long id = Long.valueOf(testCommentId);
        if (!testCommentRepository.existsById(id)) {
            throw new RuntimeException("Comment not found");
        }
        testCommentRepository.deleteById(testCommentId);
    }
}
