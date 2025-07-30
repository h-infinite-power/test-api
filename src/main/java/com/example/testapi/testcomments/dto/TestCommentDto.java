package com.example.testapi.testcomments.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class TestCommentDto {
    @Getter
    @Setter
    public static class Request {
        @JsonSerialize(using = ToStringSerializer.class)
        @Schema(type = "string")
        private Long testMemberId;
        private String testComment;
    }

    @Getter
    @Setter
    public static class UpdateRequest {
        private String testComment;
    }

    @Getter
    @Setter
    public static class Response {
        @JsonSerialize(using = ToStringSerializer.class)
        @Schema(type = "string")
        private Long testCommentId;
    }
}
