package yerong.wedle.block.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.block.dto.BlockRequest;
import yerong.wedle.block.dto.BlockedMemberResponse;
import yerong.wedle.block.service.BlockService;

@Tag(name = "Block API", description = "차단 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/block")
public class BlockApiController {
    private final BlockService blockService;

    @Operation(summary = "차단 생성", description = "사용자를 차단합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "신고가 성공적으로 생성되었습니다."),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음"),
            @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없음"),
            @ApiResponse(responseCode = "409", description = "이미 신고를 했음")
    })
    @PostMapping
    public ResponseEntity<String> createBoard(@RequestBody BlockRequest blockRequest) {
        blockService.blockMember(blockRequest);
        return ResponseEntity.ok("Block이 성공적으로 생성되었습니다.");
    }

    @Operation(summary = "차단 해제", description = "차단된 사용자를 해제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "차단이 성공적으로 해제되었습니다."),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음"),
            @ApiResponse(responseCode = "404", description = "차단된 회원을 찾을 수 없음"),
            @ApiResponse(responseCode = "404", description = "차단된 것을 찾을 수 없음")
    })
    @PostMapping("/unblock")
    public ResponseEntity<String> unblockMember(@RequestBody BlockRequest blockRequest) {
        blockService.unblockMember(blockRequest);
        return ResponseEntity.ok("Block이 성공적으로 해제되었습니다.");
    }

    @Operation(summary = "차단된 회원 목록 조회", description = "현재 사용자가 차단한 회원들의 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "차단된 회원 목록 조회 성공"),
            @ApiResponse(responseCode = "404", description = "회원 정보를 찾을 수 없음")
    })
    @GetMapping("/blocked")
    public ResponseEntity<BlockedMemberResponse> getBlockedMembers() {
        BlockedMemberResponse blockedMemberResponse = blockService.getBlockedMembers();
        return ResponseEntity.ok(blockedMemberResponse);
    }

}
