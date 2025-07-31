package com.example.testapi.testattendances.controller;

import com.example.testapi.common.swagger.SwaggerParameter;
import com.example.testapi.testattendances.dto.TestAttendanceDto;
import com.example.testapi.testattendances.service.TestAttendanceService;
import com.example.testapi.testlikes.dto.TestLikeDto;
import com.example.testapi.testlikes.service.TestLikeService;
import com.example.testapi.testcomments.dto.TestCommentDto;
import com.example.testapi.testcomments.service.TestCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "출석 체크 API")
@RestController
@RequestMapping("/test-api/test-attendances")
@RequiredArgsConstructor
public class TestAttendanceController {
    private final TestAttendanceService testAttendanceService;
    private final TestLikeService testLikeService;
    private final TestCommentService testCommentService;

    @Operation(
        summary = "출석 체크 등록",
        description = "새로운 출석 체크를 등록합니다. 날짜는 요청 당일 날짜로 자동 생성됩니다."
    )
    @ApiResponse(
        responseCode = "201",
        description = "출석 체크 등록 성공",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            examples = @ExampleObject(
                value = """
                    {
                      "testMemberId": 10
                    }"""
            )
        )
    )
    @PostMapping
    public ResponseEntity<TestAttendanceDto.Response> createAttendance(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "출석 체크 등록 정보",
                required = true,
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(
                        value = """
                            {
                              "testMemberId": 1
                            }"""
                    )
                )
            )
            @RequestBody TestAttendanceDto.Request request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(testAttendanceService.createAttendance(request));
    }

    @Operation(
        summary = "출석 체크 전체 조회",
        description = "모든 출석 체크 목록을 조회합니다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "출석 체크 전체 조회 성공",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            examples = @ExampleObject(
                value = """
                    [
                        {
                            "testAttendanceId": 10,
                            "testMemberId": 1,
                            "testAttendanceDate": "2025-07-29",
                            "testLikesCount": 2,
                            "testCommentsCount": 3
                        }
                    ]"""
            )
        )
    )
    @GetMapping
    public ResponseEntity<List<TestAttendanceDto.Response>> getAllAttendances() {
        return ResponseEntity.ok(testAttendanceService.getAllAttendances());
    }

    @Operation(
        summary = "출석 체크 상세 조회",
        description = "특정 출석 체크의 상세 정보를 조회합니다."
    )
    @Parameters({
        @Parameter(name = "testAttendanceId", description = "조회할 출석 체크의 ID", example = SwaggerParameter.TEST_ATTENDANCE_ID_EXAMPLE)
    })
    @ApiResponse(
        responseCode = "200",
        description = "출석 체크 상세 조회 성공",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            examples = @ExampleObject(
                value = """
                    {
                        "testAttendanceId": 10,
                        "testMemberId": 1,
                        "testAttendanceDate": "2025-07-29",
                        "testLikes": [
                            {
                                "testLikeId": 1,
                                "testMemberId": 2,
                                "testMemberName": "홍길동"
                            }
                        ],
                        "testComments": [
                            {
                                "testCommentId": 1,
                                "testMemberId": 2,
                                "testMemberName": "홍길동",
                                "testComment": "수고하셨습니다!"
                            }
                        ]
                    }"""
            )
        )
    )
    @ApiResponse(
        responseCode = "404",
        description = "출석 체크를 찾을 수 없음",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            examples = @ExampleObject(
                value = """
                    {
                      "error": "Attendance not found"
                    }"""
            )
        )
    )
    @GetMapping("/{testAttendanceId}")
    public ResponseEntity<TestAttendanceDto.DetailResponse> getAttendanceDetail(
            @PathVariable String testAttendanceId) {
        return ResponseEntity.ok(testAttendanceService.getAttendanceDetail(testAttendanceId));
    }

    @Operation(
        summary = "출석 체크 삭제",
        description = "특정 출석 체크를 삭제합니다."
    )
    @Parameters({
        @Parameter(name = "testAttendanceId", description = "삭제할 출석 체크의 ID", example = SwaggerParameter.TEST_ATTENDANCE_ID_EXAMPLE)
    })
    @ApiResponse(
        responseCode = "204",
        description = "출석 체크 삭제 성공"
    )
    @ApiResponse(
        responseCode = "404",
        description = "출석 체크를 찾을 수 없음",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            examples = @ExampleObject(
                value = """
                    {
                      "error": "Attendance not found"
                    }"""
            )
        )
    )
    @DeleteMapping("/{testAttendanceId}")
    public ResponseEntity<Void> deleteAttendance(
            @PathVariable Long testAttendanceId) {
        testAttendanceService.deleteAttendance(testAttendanceId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "출석 체크 좋아요 추가",
        description = "특정 출석 체크에 좋아요를 추가합니다."
    )
    @Parameters({
        @Parameter(in = ParameterIn.PATH, name = "testAttendanceId", description = "좋아요를 추가할 출석 체크의 ID", example = SwaggerParameter.TEST_ATTENDANCE_ID_EXAMPLE)
    })
    @ApiResponse(
        responseCode = "201",
        description = "좋아요 추가 성공",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            examples = @ExampleObject(
                value = """
                    {
                      "testLikeId": """ + SwaggerParameter.TEST_LIKE_ID_EXAMPLE + """
                    }"""
            )
        )
    )
    @ApiResponse(
        responseCode = "404",
        description = "출석 체크를 찾을 수 없음",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            examples = @ExampleObject(
                value = """
                    {
                      "error": "Attendance not found"
                    }"""
            )
        )
    )
    @PostMapping("/{testAttendanceId}/test-likes")
    public ResponseEntity<TestLikeDto.Response> addLike(
            @PathVariable Long testAttendanceId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "좋아요 추가 정보",
                required = true,
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(
                        value = """
                            {
                              "testMemberId": 10
                            }"""
                    )
                )
            )
            @RequestBody TestLikeDto.Request request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(testLikeService.createLike(testAttendanceId, request));
    }

    @Operation(
        summary = "댓글 등록",
        description = "특정 출석 체크에 댓글을 등록합니다."
    )
    @Parameters({
        @Parameter(in = ParameterIn.PATH, name = "testAttendanceId", description = "댓글을 추가할 출석 체크의 ID", example = SwaggerParameter.TEST_ATTENDANCE_ID_EXAMPLE)
    })
    @ApiResponse(
        responseCode = "201",
        description = "댓글 등록 성공",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            examples = @ExampleObject(
                value = """
                    {
                      "testCommentId": """ + SwaggerParameter.TEST_COMMENT_ID_EXAMPLE + """
                    }"""
            )
        )
    )
    @PostMapping("/{testAttendanceId}/test-comments")
    public ResponseEntity<TestCommentDto.Response> addComment(
            @PathVariable Long testAttendanceId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "댓글 등록 정보",
                required = true,
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(
                        value = """
                            {
                              "testMemberId": 2,
                              "testComment": "오늘도 수고하셨습니다!"
                            }"""
                    )
                )
            )
            @RequestBody TestCommentDto.Request request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(testCommentService.createComment(testAttendanceId, request));
    }
}
