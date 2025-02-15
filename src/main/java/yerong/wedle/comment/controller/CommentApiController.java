package yerong.wedle.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.comment.dto.CommentRequest;
import yerong.wedle.comment.dto.CommentResponse;
import yerong.wedle.comment.service.CommentService;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@Tag(name = "Comment API", description = "댓글 API")
public class CommentApiController {
    private final CommentService commentService;

    @Operation(summary = "댓글 생성", description = "댓글을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글이 성공적으로 생성되었습니다."),
            @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없음")
    })
    @PostMapping
    public ResponseEntity<CommentResponse> createComment(CommentRequest commentRequest) {
        CommentResponse comment = commentService.createComment(commentRequest);
        return ResponseEntity.ok().body(comment);

    }

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글이 성공적으로 삭제되었습니다."),
            @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없음")
    })
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");
    }

    @Operation(summary = "해당 게시글의 댓글 조회", description = "해당 게시글의 댓글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 목록을 성공적으로 반환했습니다.")
    })
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long postId) {
        List<CommentResponse> comments = commentService.getComments(postId);
        return ResponseEntity.ok().body(comments);
    }

    @Operation(summary = "댓글 조회", description = "댓글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글을 성공적으로 반환했습니다.")
    })
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponse> getComment(@PathVariable Long commentId) {
        CommentResponse comment = commentService.getComment(commentId);
        return ResponseEntity.ok().body(comment);
    }
}
