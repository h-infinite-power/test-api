package com.example.testapi.testlikes.dto;

import lombok.Getter;
import lombok.Setter;

public class TestLikeDto {
    @Getter
    @Setter
    public static class Request {
        private String testMemberId;
    }

    @Getter
    @Setter
    public static class Response {
        private String testLikeId;
    }
}
