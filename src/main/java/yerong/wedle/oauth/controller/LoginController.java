package yerong.wedle.oauth.controller;

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

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final AuthService authService;

    @PostMapping("/login/apple")
    public ResponseEntity<?> login(@RequestBody MemberRequest memberRequest) throws Exception {
        try {
            log.info("============ start =============");
            log.info("member request username : " + memberRequest.getName());
            log.info("member request socialId : " + memberRequest.getSocialId());
            log.info("member request email : " + memberRequest.getEmail());

            TokenResponse tokenResponse = authService.login(memberRequest);

            log.info("============token response =============");
            log.info(tokenResponse.toString());
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
