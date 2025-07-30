package com.example.testapi.testattendances.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

public class TestAttendanceDto {
    @Getter
    @Setter
    public static class Request {
        @JsonSerialize(using = ToStringSerializer.class)
        @Schema(type = "string")
        private Long testMemberId;
    }

    @Getter
    @Setter
    public static class Response {
        @JsonSerialize(using = ToStringSerializer.class)
        @Schema(type = "string")
        private Long testAttendanceId;
        @JsonSerialize(using = ToStringSerializer.class)
        @Schema(type = "string")
        private Long testMemberId;
        private LocalDate testAttendanceDate;
        private int testLikesCount;
        private int testCommentsCount;
    }

    @Getter
    @Setter
    public static class DetailResponse {
        @JsonSerialize(using = ToStringSerializer.class)
        @Schema(type = "string")
        private Long testAttendanceId;
        @JsonSerialize(using = ToStringSerializer.class)
        @Schema(type = "string")
        private Long testMemberId;
        private LocalDate testAttendanceDate;
        private List<TestLikeInfo> testLikes;
        private List<TestCommentInfo> testComments;
    }

    @Getter
    @Setter
    public static class TestLikeInfo {
        @JsonSerialize(using = ToStringSerializer.class)
        @Schema(type = "string")
        private Long testLikeId;
        @JsonSerialize(using = ToStringSerializer.class)
        @Schema(type = "string")
        private Long testMemberId;
        private String testMemberName;
    }

    @Getter
    @Setter
    public static class TestCommentInfo {
        @JsonSerialize(using = ToStringSerializer.class)
        @Schema(type = "string")
        private Long testCommentId;
        @JsonSerialize(using = ToStringSerializer.class)
        @Schema(type = "string")
        private Long testMemberId;
        private String testMemberName;
        private String testComment;
    }
}
