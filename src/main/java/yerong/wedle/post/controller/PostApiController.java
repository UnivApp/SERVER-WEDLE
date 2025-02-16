package yerong.wedle.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.post.dto.HotPostResponse;
import yerong.wedle.post.dto.PostCreateRequest;
import yerong.wedle.post.dto.PostResponse;
import yerong.wedle.post.dto.PostUpdateRequest;
import yerong.wedle.post.service.PostService;

@Tag(name = "Post API", description = "게시글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostApiController {
    private final PostService postService;

    @Operation(summary = "게시글 생성", description = "게시글을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글이 성공적으로 생성되었습니다."),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음")
    })
    @PostMapping
    public ResponseEntity<PostResponse> createPost(PostCreateRequest postCreateRequest) {
        PostResponse post = postService.createPost(postCreateRequest);
        return ResponseEntity.ok().body(post);
    }

    @Operation(summary = "게시글 수정", description = "게시글을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글이 성공적으로 수정되었습니다."),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음")
    })
    @PutMapping
    public ResponseEntity<PostResponse> updatePost(PostUpdateRequest postUpdateRequest) {
        PostResponse postResponse = postService.updatePost(postUpdateRequest);
        return ResponseEntity.ok().body(postResponse);
    }

    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글이 성공적으로 삭제되었습니다."),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음")
    })
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok("게시글이 성공적으로 삭제되었습니다.");
    }

    @Operation(summary = "해당 게시판의 게시글 조회", description = "해당 게시판의 게시글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 목록을 성공적으로 반환했습니다.")
    })
    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<PostResponse>> getAllPosts(@PathVariable Long boardId) {
        List<PostResponse> allPosts = postService.getAllPosts(boardId);
        return ResponseEntity.ok(allPosts);
    }

    @Operation(summary = "해당 HOT 게시판의 게시글 조회", description = "해당 HOT 게시판의 게시글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 목록을 성공적으로 반환했습니다.")
    })
    @GetMapping("/hot-board")
    public ResponseEntity<List<HotPostResponse>> getAllHotPosts() {
        try {
            System.out.println("Entering getAllHotPosts");
            List<HotPostResponse> allPosts = postService.getAllHotPosts();
            System.out.println("Post fetched successfully: " + allPosts.size());
            return ResponseEntity.ok(allPosts);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // 에러 메시지 반환
        }
    }

    @Operation(summary = "게시글 조회", description = "게시글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글을 성공적으로 반환했습니다.")
    })
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        PostResponse post = postService.getPost(postId);
        return ResponseEntity.ok(post);
    }
}
