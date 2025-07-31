package com.example.testapi.testmembers.controller;

import com.example.testapi.common.swagger.SwaggerParameter;
import com.example.testapi.testmembers.dto.TestMemberDto;
import com.example.testapi.testmembers.service.TestMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "구성원 API")
@RestController
@RequestMapping("/test-api/test-members")
@RequiredArgsConstructor
public class TestMemberController {
    private final TestMemberService testMemberService;

    @Operation(
        summary = "구성원 등록",
        description = "새로운 구성원을 등록합니다."
    )
    @ApiResponse(
        responseCode = "201",
        description = "구성원 등록 성공",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            examples = @ExampleObject(
                value = "{\n  \"testMemberId\": " + SwaggerParameter.TEST_MEMBER_ID_EXAMPLE + "\n}"
            )
        )
    )
    @PostMapping
    public ResponseEntity<TestMemberDto.Response> createMember(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "구성원 등록 정보",
                required = true,
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(
                        value = """
                            {
                              "testMemberName": "홍길동"
                            }"""
                    )
                )
            )
            @RequestBody TestMemberDto.Request request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(testMemberService.createMember(request));
    }

    @Operation(
        summary = "구성원 전체 조회",
        description = "모든 구성원 목록을 조회합니다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "구성원 전체 조회 성공",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            examples = @ExampleObject(
                value = "[\n  {\n    \"testMemberId\": " + SwaggerParameter.TEST_MEMBER_ID_EXAMPLE + ",\n    \"testMemberName\": \"홍길동\"\n  },\n  {\n    \"testMemberId\": " + SwaggerParameter.TEST_MEMBER_ID_EXAMPLE_2 + ",\n    \"testMemberName\": \"김철수\"\n  }\n]"
            )
        )
    )
    @GetMapping
    public ResponseEntity<List<TestMemberDto.Response>> getAllMembers() {
        return ResponseEntity.ok(testMemberService.getAllMembers());
    }

    @Operation(
        summary = "구성원 조회",
        description = "구성원 ID로 구성원 정보를 조회합니다."
    )
    @Parameters({
        @Parameter(in = ParameterIn.PATH, name = "testMemberId", description = "조회할 구성원의 ID", example = SwaggerParameter.TEST_MEMBER_ID_EXAMPLE)
    })
    @ApiResponse(
        responseCode = "200",
        description = "구성원 조회 성공",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            examples = @ExampleObject(
                value = "{\n  \"testMemberId\": " + SwaggerParameter.TEST_MEMBER_ID_EXAMPLE + ",\n  \"testMemberName\": \"홍길동\"\n}"
            )
        )
    )
    @GetMapping("/{testMemberId}")
    public ResponseEntity<TestMemberDto.Response> getMember(
            @PathVariable Long testMemberId) {
        return ResponseEntity.ok(testMemberService.getMember(testMemberId));
    }

    @Operation(
        summary = "구성원 수정",
        description = "구성원의 정보를 수정합니다."
    )
    @Parameters({
        @Parameter(in = ParameterIn.PATH, name = "testMemberId", description = "수정할 구성원의 ID", example = SwaggerParameter.TEST_MEMBER_ID_EXAMPLE)
    })
    @ApiResponse(
        responseCode = "200",
        description = "구성원 수정 성공",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            examples = @ExampleObject(
                value = "{\n  \"testMemberId\": " + SwaggerParameter.TEST_MEMBER_ID_EXAMPLE + ",\n  \"testMemberName\": \"홍길동 수정\"\n}"
            )
        )
    )
    @PutMapping("/{testMemberId}")
    public ResponseEntity<TestMemberDto.Response> updateMember(
            @PathVariable Long testMemberId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "수정할 구성원 정보",
                required = true,
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(
                        value = """
                            {
                              "testMemberName": "홍길동 수정"
                            }"""
                    )
                )
            )
            @RequestBody TestMemberDto.Request request) {
        return ResponseEntity.ok(testMemberService.updateMember(testMemberId, request));
    }

    @Operation(
        summary = "구성원 삭제",
        description = "구성원을 삭제합니다."
    )
    @Parameters({
        @Parameter(in = ParameterIn.PATH, name = "testMemberId", description = "삭제할 구성원의 ID", example = SwaggerParameter.TEST_MEMBER_ID_EXAMPLE)
    })
    @ApiResponse(
        responseCode = "204",
        description = "구성원 삭제 성공"
    )
    @DeleteMapping("/{testMemberId}")
    public ResponseEntity<Void> deleteMember(
            @PathVariable Long testMemberId) {
        testMemberService.deleteMember(testMemberId);
        return ResponseEntity.noContent().build();
    }
}
