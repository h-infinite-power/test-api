package com.example.testapi.testmembers.dto;

import lombok.Getter;
import lombok.Setter;

public class TestMemberDto {
    @Getter
    @Setter
    public static class Request {
        private String testMemberName;
    }

    @Getter
    @Setter
    public static class Response {
        private String testMemberId;
        private String testMemberName;
    }
}
