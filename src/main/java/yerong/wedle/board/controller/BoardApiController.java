package yerong.wedle.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.board.dto.BoardRequest;
import yerong.wedle.board.dto.BoardResponse;
import yerong.wedle.board.service.BoardService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardApiController {

    private final BoardService boardService;

    @Operation(summary = "게시판 생성", description = "게시판을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시판이 성공적으로 생성되었습니다."),
            @ApiResponse(responseCode = "400", description = "게시판을 찾을 수 없습니다.")
    })
    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(BoardRequest boardRequest) {
        BoardResponse board = boardService.createBoard(boardRequest);
        return ResponseEntity.ok(board);
    }

    @Operation(summary = "게시판 삭제", description = "게시판을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시판이 성공적으로 삭제되었습니다."),
            @ApiResponse(responseCode = "404", description = "게시판을 찾을 수 없음")
    })
    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok("게시판이 성공적으로 삭제되었습니다.");
    }

    @Operation(summary = "전체 게시판 조회", description = "모든 게시판을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시판 목록을 성공적으로 반환했습니다.")
    })
    @GetMapping
    public ResponseEntity<List<BoardResponse>> getAllBoards() {
        List<BoardResponse> boards = boardService.getBoards();
        return ResponseEntity.ok(boards);
    }

    @Operation(summary = "게시판 조회", description = "특정 ID의 게시판을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시판을 성공적으로 반환했습니다."),
            @ApiResponse(responseCode = "404", description = "게시판을 찾을 수 없음")
    })
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponse> getBoardById(@PathVariable Long boardId) {
        BoardResponse board = boardService.getBoardById(boardId);
        return ResponseEntity.ok(board);
    }
}
