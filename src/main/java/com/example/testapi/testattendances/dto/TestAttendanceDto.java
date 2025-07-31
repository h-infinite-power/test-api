package com.example.testapi.testattendances.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;


@NoArgsConstructor
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
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        @JsonSerialize(using = ToStringSerializer.class)
        @Schema(type = "string")
        private Long testAttendanceId;
        @JsonSerialize(using = ToStringSerializer.class)
        @Schema(type = "string")
        private Long testMemberId;
        private LocalDate testAttendanceDate;
        private Long testLikesCount;
        private Long testCommentsCount;
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestLikeInfo that = (TestLikeInfo) o;
            return testLikeId != null && testLikeId.equals(that.testLikeId);
        }

        @Override
        public int hashCode() {
            return testLikeId != null ? testLikeId.hashCode() : 0;
        }
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestCommentInfo that = (TestCommentInfo) o;
            return testCommentId != null && testCommentId.equals(that.testCommentId);
        }

        @Override
        public int hashCode() {
            return testCommentId != null ? testCommentId.hashCode() : 0;
        }
    }
}
