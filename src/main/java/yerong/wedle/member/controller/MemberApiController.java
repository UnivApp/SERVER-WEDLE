package yerong.wedle.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.member.dto.NicknameDuplicateResponse;
import yerong.wedle.member.dto.NicknameRequest;
import yerong.wedle.member.dto.NicknameResponse;
import yerong.wedle.member.service.MemberService;

@Tag(name = "Member API", description = "회원 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;

    @Operation(summary = "회원 닉네임 등록", description = "신규 회원의 닉네임을 등록합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "닉네임 등록 성공"),
            @ApiResponse(responseCode = "400", description = "닉네임에 공백이 포함됨"),
            @ApiResponse(responseCode = "400", description = "기존 닉네임과 동일함"),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음"),
            @ApiResponse(responseCode = "409", description = "중복된 닉네임이 있음")

    })
    @PostMapping("/nickname")
    public ResponseEntity<NicknameResponse> createNickname(NicknameRequest nickNameRequest) {
        return ResponseEntity.ok().body(memberService.setNickname(nickNameRequest));
    }

    @Operation(summary = "닉네임 변경", description = "회원의 닉네임을 변경합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "닉네임 변경 성공"),
            @ApiResponse(responseCode = "400", description = "변경하려는 닉네임이 기존 닉네임과 같음"),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음"),
            @ApiResponse(responseCode = "409", description = "중복된 닉네임이 있음")
    })
    @PutMapping("/nickname")
    public ResponseEntity<NicknameResponse> updateNickname(@RequestBody NicknameRequest nicknameRequest) {
        return ResponseEntity.ok(memberService.setNickname(nicknameRequest));
    }

    @Operation(summary = "닉네임 중복 체크", description = "닉네임의 중복 여부를 확인합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "닉네임 중복 확인 성공")
    })
    @GetMapping("/nickname/check")
    public ResponseEntity<NicknameDuplicateResponse> checkNicknameDuplicate(@RequestParam String nickname) {
        NicknameDuplicateResponse nicknameDuplicateResponse = memberService.checkNicknameDuplicate(nickname);
        return ResponseEntity.ok(nicknameDuplicateResponse);
    }

    @GetMapping("/nickname")
    public ResponseEntity<NicknameResponse> getNickname() {
        return ResponseEntity.ok(memberService.getNickname());
    }
}
