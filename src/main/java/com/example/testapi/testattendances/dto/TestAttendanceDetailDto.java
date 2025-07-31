package com.example.testapi.testattendances.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TestAttendanceDetailDto {
    private Long testAttendanceId;
    private Long testMemberId;
    private String testMemberName;
    private LocalDate testAttendanceDate;
    private Long testLikeId;
    private Long likeTestMemberId;
    private String likeMemberName;
    private Long testCommentId;
    private Long commentTestMemberId;
    private String commentMemberName;
    private String testComment;

    public TestAttendanceDetailDto(Long testAttendanceId, Long testMemberId, String testMemberName, 
                              LocalDate testAttendanceDate, Long testLikeId, Long likeTestMemberId, 
                              String likeMemberName, Long testCommentId, Long commentTestMemberId, 
                              String commentMemberName, String testComment) {
        this.testAttendanceId = testAttendanceId;
        this.testMemberId = testMemberId;
        this.testMemberName = testMemberName;
        this.testAttendanceDate = testAttendanceDate;
        this.testLikeId = testLikeId;
        this.likeTestMemberId = likeTestMemberId;
        this.likeMemberName = likeMemberName;
        this.testCommentId = testCommentId;
        this.commentTestMemberId = commentTestMemberId;
        this.commentMemberName = commentMemberName;
        this.testComment = testComment;
    }
}
