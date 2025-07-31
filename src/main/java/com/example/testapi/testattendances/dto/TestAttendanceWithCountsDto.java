package com.example.testapi.testattendances.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TestAttendanceWithCountsDto {
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(type = "string")
    private Long testAttendanceId;
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(type = "string")
    private Long testMemberId;
    private LocalDate testAttendanceDate;
    private Long testLikesCount;
    private Long testCommentsCount;

    public TestAttendanceWithCountsDto(Long testAttendanceId, Long testMemberId,
                                    LocalDate testAttendanceDate, Long testLikesCount, Long testCommentsCount) {
        this.testAttendanceId = testAttendanceId;
        this.testMemberId = testMemberId;
        this.testAttendanceDate = testAttendanceDate;
        this.testLikesCount = testLikesCount;
        this.testCommentsCount = testCommentsCount;
    }

}
