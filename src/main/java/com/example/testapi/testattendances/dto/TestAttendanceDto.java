package com.example.testapi.testattendances.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

public class TestAttendanceDto {
    @Getter
    @Setter
    public static class Request {
        private String testMemberId;
    }

    @Getter
    @Setter
    public static class Response {
        private String testAttendanceId;
        private String testMemberId;
        private LocalDate testAttendanceDate;
        private int testLikesCount;
        private int testCommentsCount;
    }

    @Getter
    @Setter
    public static class DetailResponse {
        private String testAttendanceId;
        private String testMemberId;
        private LocalDate testAttendanceDate;
        private List<TestLikeInfo> testLikes;
        private List<TestCommentInfo> testComments;
    }

    @Getter
    @Setter
    public static class TestLikeInfo {
        private String testLikeId;
        private String testMemberId;
        private String testMemberName;
    }

    @Getter
    @Setter
    public static class TestCommentInfo {
        private String testCommentId;
        private String testMemberId;
        private String testMemberName;
        private String testComment;
    }
}
