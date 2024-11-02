package yerong.wedle.oauth.controller;

import com.nimbusds.oauth2.sdk.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import yerong.wedle.common.exception.ErrorResponse;
import yerong.wedle.member.dto.MemberRequest;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.oauth.dto.LoginResponse;
import yerong.wedle.oauth.dto.LoginStatusResponse;
import yerong.wedle.oauth.dto.MemberLogoutResponse;
import yerong.wedle.oauth.dto.TokenResponse;
import yerong.wedle.oauth.exception.InvalidRefreshTokenException;
import yerong.wedle.oauth.exception.InvalidTokenException;
import yerong.wedle.oauth.service.AuthService;
import yerong.wedle.oauth.service.JwtBlacklistService;

import java.security.Principal;
import java.util.Map;

@Tag(name = "Authentication API", description = "로그인 및 토큰 갱신 관련 API")
@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final AuthService authService;
    private final JwtBlacklistService jwtBlacklistService;

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
            LoginResponse loginResponse = authService.login(memberRequest);
            HttpHeaders headers = authService.setTokenHeaders(TokenResponse.builder()
                    .accessToken(loginResponse.getAccessToken())
                    .refreshToken(loginResponse.getRefreshToken()).build());

            return ResponseEntity.ok().headers(headers).body(loginResponse);
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

            return ResponseEntity.ok().headers(headers).body(tokenResponse);
        } catch (InvalidRefreshTokenException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 Refresh Token입니다.");
        } catch (MemberNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원 정보를 찾을 수 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("토큰 갱신 중 오류가 발생했습니다.");
        }
    }
    @PostMapping("/login/status")
    public ResponseEntity<LoginStatusResponse> checkLoginStatus(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String accessToken = authService.extractAccessTokenFromHeader(authorizationHeader);

        try {
            if (jwtBlacklistService.isTokenBlacklisted(accessToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new LoginStatusResponse(false, false, "토큰이 유효하지 않습니다. 다시 로그인 해주세요."));
            }

            boolean isLoggedIn = authService.isLoggedIn();

            if (!isLoggedIn) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new LoginStatusResponse(false, false, "사용자는 로그인 상태가 아닙니다."));
            }

            boolean hasNickname = authService.hasNickname();
            String message = hasNickname ? "사용자는 완벽하게 회원가입 후 로그인된 상태입니다." :
                    "닉네임이 없는 상태로 회원가입이 완료되었습니다. 닉네임 입력이 필요합니다.";

            return ResponseEntity.ok(new LoginStatusResponse(true, hasNickname, message));

        } catch (InvalidTokenException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new LoginStatusResponse(false, false, "유효하지 않은 토큰입니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginStatusResponse(false, false, "상태 확인 중 오류가 발생했습니다."));
        }
    }


    @Operation(
            summary = "로그아웃",
            description = "현재 유효한 액세스 토큰과 리프레시 토큰을 블랙리스트에 등록하여 로그아웃 처리합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "요청 헤더에서 유효하지 않은 액세스 토큰 또는 이미 블랙리스트에 등록된 토큰",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "해당 Social ID로 회원을 찾을 수 없음",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류로 인해 로그아웃 실패",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/member/logout")
    public ResponseEntity<?> logout(Principal principal, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        String accessToken = authService.extractAccessTokenFromHeader(authorizationHeader);

        if (accessToken == null) {
            log.warn("요청 헤더에서 액세스 토큰을 찾을 수 없음.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "요청 헤더에서 액세스 토큰을 찾을 수 없음."));
        }

        if (jwtBlacklistService.isTokenBlacklisted(accessToken)) {
            log.warn("이미 블랙리스트에 있는 액세스 토큰입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "이미 블랙리스트에 있는 액세스 토큰입니다."));
        }

        if (!authService.isTokenValid(accessToken)) {
            log.warn("유효하지 않은 액세스 토큰: {}", accessToken);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "유효하지 않은 토큰입니다."));
        }

        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();
        MemberLogoutResponse memberLogoutResponse = authService.logout(socialId);
        if (memberLogoutResponse == null) {
            log.warn("Social Id {}로 회원을 찾을 수 없음", principal.getName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Social Id로 회원을 찾을 수 없습니다."));
        }

        try {
            String refreshToken = memberLogoutResponse.getRefreshToken();

            jwtBlacklistService.addTokenToBlacklist(accessToken);
            if (refreshToken != null) {
                jwtBlacklistService.addTokenToBlacklist(refreshToken);
            }
            SecurityContextHolder.clearContext();
            log.info("로그아웃 성공");
            return ResponseEntity.ok(Map.of("message", "로그아웃에 성공하였습니다."));
        } catch (Exception e) {
            log.error("로그아웃 실패: 사용자 {}", principal.getName(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "로그아웃 실패하였습니다."));
        }
    }
    @Operation(
            summary = "회원탈퇴",
            description = "회원 정보를 삭제하고, 관련된 리프레시 토큰을 무효화합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원탈퇴 성공"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰"),
            @ApiResponse(responseCode = "404", description = "회원 정보를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    @DeleteMapping("/member/delete")
    public ResponseEntity<?> deleteMember() {
        try {
            authService.deleteMember();
            SecurityContextHolder.clearContext();
            return ResponseEntity.ok().body("회원탈퇴가 완료되었습니다.");
        } catch (InvalidTokenException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        } catch (MemberNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 회원 정보를 찾을 수 없습니다.");
        } catch (Exception e) {
            log.error("회원탈퇴 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원탈퇴 중 오류가 발생했습니다.");
        }
    }


}
