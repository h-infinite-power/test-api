package com.example.testapi.testcomments.controller;

import com.example.testapi.common.swagger.SwaggerParameter;
import com.example.testapi.testcomments.dto.TestCommentDto;
import com.example.testapi.testcomments.service.TestCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "댓글 API")
@RestController
@RequestMapping("/test-api/test-comments")
@RequiredArgsConstructor
public class TestCommentController {
    private final TestCommentService testCommentService;

    @Operation(
        summary = "댓글 수정",
        description = "특정 댓글을 수정합니다."
    )
    @Parameters({
        @Parameter(in = ParameterIn.PATH, name = "testCommentId", description = "수정할 댓글의 ID", example = SwaggerParameter.TEST_COMMENT_ID_EXAMPLE)
    })
    @ApiResponse(
        responseCode = "200",
        description = "댓글 수정 성공",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            examples = @ExampleObject(
                value = "{\n  \"testCommentId\": " + SwaggerParameter.TEST_COMMENT_ID_EXAMPLE + "\n}"
            )
        )
    )
    @PutMapping("/{testCommentId}")
    public ResponseEntity<TestCommentDto.Response> updateComment(
            @PathVariable Long testCommentId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "수정할 댓글 정보",
                required = true,
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(
                        value = """
                            {
                              "testComment": "정말 고생 많으셨습니다!"
                            }"""
                    )
                )
            )
            @RequestBody TestCommentDto.UpdateRequest request) {
        return ResponseEntity.ok(testCommentService.updateComment(testCommentId, request));
    }

    @Operation(
        summary = "댓글 삭제",
        description = "특정 댓글을 삭제합니다. 삭제 후에는 다시 출석 체크 상세조회를 통해 댓글 수 및 댓글 내용을 확인할 수 있습니다."
    )
    @Parameters({
        @Parameter(in = ParameterIn.PATH, name = "testCommentId", description = "삭제할 댓글의 ID", example = SwaggerParameter.TEST_COMMENT_ID_EXAMPLE)
    })
    @ApiResponse(
        responseCode = "204",
        description = "댓글 삭제 성공"
    )
    @DeleteMapping("/{testCommentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long testCommentId) {
        testCommentService.deleteComment(testCommentId);
        return ResponseEntity.noContent().build();
    }
}
