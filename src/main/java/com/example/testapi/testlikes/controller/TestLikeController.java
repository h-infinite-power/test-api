package com.example.testapi.testlikes.controller;

import com.example.testapi.testlikes.service.TestLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "좋아요 API")
@RestController
@RequestMapping("/test-api/test-likes")
@RequiredArgsConstructor
public class TestLikeController {
    private final TestLikeService testLikeService;

    @Operation(
        summary = "출석 체크 좋아요 취소",
        description = "출석 체크의 좋아요를 취소합니다. 삭제 후에는 다시 출석 체크 상세조회를 통해 좋아요 수 및 좋아요 누른 사람 데이터를 확인할 수 있습니다."
    )
    @ApiResponse(
        responseCode = "204",
        description = "좋아요 취소 성공"
    )
    @ApiResponse(
        responseCode = "404",
        description = "좋아요를 찾을 수 없음",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            examples = @ExampleObject(
                value = """
                    {
                      "error": "Like not found"
                    }"""
            )
        )
    )
    @DeleteMapping("/{testLikeId}")
    public ResponseEntity<Void> deleteLike(
            @Parameter(description = "삭제할 좋아요의 ID", example = "10")
            @PathVariable Long testLikeId) {
        testLikeService.deleteLike(testLikeId);
        return ResponseEntity.noContent().build();
    }
}
