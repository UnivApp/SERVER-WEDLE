package yerong.wedle.like.commentLike.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.like.commentLike.service.CommentLikeService;

@Tag(name = "Comment Like API", description = "댓글 좋아요 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comment-like")
@Slf4j
public class CommentLikeApiController {
    private final CommentLikeService commentLikeService;

    @Operation(
            summary = "댓글 좋아요 생성",
            description = "댓글 좋아요를 생성합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "좋아요 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/add")
    public ResponseEntity<Void> addStar(@RequestParam Long commentId) {
        try {
            commentLikeService.addLike(commentId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("좋아요 생성 실패");
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @Operation(
            summary = "댓글 좋아요 삭제",
            description = "댓글 좋아요를 삭제합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "좋아요 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/remove")
    public ResponseEntity<Void> removeStar(@RequestParam Long commentId) {
        try {
            commentLikeService.removeLike(commentId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("좋아요 삭제 실패", e);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
