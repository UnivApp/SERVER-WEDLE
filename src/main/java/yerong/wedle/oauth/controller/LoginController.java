package yerong.wedle.oauth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.member.dto.MemberRequest;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.oauth.dto.AccessTokenResponse;
import yerong.wedle.oauth.dto.TokenResponse;
import yerong.wedle.oauth.exception.InvalidRefreshTokenException;
import yerong.wedle.oauth.service.AuthService;

@Tag(name = "Authentication API", description = "로그인 및 토큰 갱신 관련 API")
@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final AuthService authService;

    @Operation(
            summary = "Apple 로그인",
            description = "Apple 로그인 요청을 처리하고 액세스 토큰을 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공, 액세스 토큰 반환"),
            @ApiResponse(responseCode = "500", description = "로그인 중 서버 오류 발생")
    })
    @PostMapping("/login/apple")
    public ResponseEntity<?> login(@RequestBody MemberRequest memberRequest) throws Exception {
        try {
            TokenResponse tokenResponse = authService.login(memberRequest);

            HttpHeaders headers = authService.setTokenHeaders(tokenResponse);

            AccessTokenResponse accessTokenResponse = AccessTokenResponse.builder()
                    .accessToken(tokenResponse.getAccessToken())
                    .accessTokenExpiresIn(tokenResponse.getAccessTokenExpiresIn())
                    .build();

            return ResponseEntity.ok().headers(headers).body(accessTokenResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그인 중 오류가 발생했습니다.");
        }

    }

    @Operation(
            summary = "액세스 토큰 갱신",
            description = "유효한 리프레시 토큰을 사용하여 액세스 토큰을 갱신합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "토큰 갱신 성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 리프레시 토큰"),
            @ApiResponse(responseCode = "404", description = "회원 정보를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "토큰 갱신 중 서버 오류 발생")
    })
    @PostMapping("/login/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestHeader("RefreshToken") String refreshToken) {
        try {
            TokenResponse tokenResponse = authService.refreshAccessToken(refreshToken);
            HttpHeaders headers = authService.setTokenHeaders(tokenResponse);

            AccessTokenResponse accessTokenResponse = AccessTokenResponse.builder()
                    .accessToken(tokenResponse.getAccessToken())
                    .accessTokenExpiresIn(tokenResponse.getAccessTokenExpiresIn())
                    .build();

            return ResponseEntity.ok().headers(headers).body(accessTokenResponse);
        } catch (InvalidRefreshTokenException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 Refresh Token입니다.");
        } catch (MemberNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원 정보를 찾을 수 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("토큰 갱신 중 오류가 발생했습니다.");
        }
    }
}
