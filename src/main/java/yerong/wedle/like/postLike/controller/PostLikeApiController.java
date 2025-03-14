package yerong.wedle.like.postLike.controller;

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
import yerong.wedle.like.postLike.service.PostLikeService;

@Tag(name = "Post Like API", description = "게시글 좋아요 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post-like")
@Slf4j
public class PostLikeApiController {
    private final PostLikeService postLikeService;

    @Operation(
            summary = "게시글 좋아요 생성",
            description = "게시글 좋아요를 생성합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "좋아요 생성 성공"),
            @ApiResponse(responseCode = "404", description = "회원 또는 게시글을 찾을 수 없습니다."),
    })
    @PostMapping("/add")
    public ResponseEntity<Void> addStar(@RequestParam Long postId) {
        try {
            postLikeService.addLike(postId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("좋아요 생성 실패");
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @Operation(
            summary = "게시글 좋아요 삭제",
            description = "게시글 좋아요를 삭제합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "좋아요 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "접근 권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "회원 또는 게시글을 찾을 수 없습니다."),
    })
    @PostMapping("/remove")
    public ResponseEntity<Void> removeStar(@RequestParam Long postId) {
        try {
            postLikeService.removeLike(postId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("좋아요 삭제 실패", e);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
