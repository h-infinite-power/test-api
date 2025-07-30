package com.example.testapi.testcomments.dto;

import lombok.Getter;
import lombok.Setter;

public class TestCommentDto {
    @Getter
    @Setter
    public static class Request {
        private String testMemberId;
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
        private String testCommentId;
    }
}
