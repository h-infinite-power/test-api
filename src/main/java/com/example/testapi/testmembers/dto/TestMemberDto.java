package com.example.testapi.testmembers.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
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
        @JsonSerialize(using = ToStringSerializer.class)
        @Schema(type = "string")
        private Long testMemberId;
        private String testMemberName;
    }
}
