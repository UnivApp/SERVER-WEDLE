package yerong.wedle.chat.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import yerong.wedle.chat.util.ChatUtil;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.exception.MemberDuplicateException;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.repository.MemberRepository;
import yerong.wedle.oauth.exception.InvalidTokenException;
import yerong.wedle.oauth.jwt.JwtProvider;

@Component
@RequiredArgsConstructor
@Slf4j
public class HttpHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtProvider jwtProvider;
    private static final String ERROR_MESSAGE = "Web Socket Connection Error!";
    private final MemberRepository memberRepository;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        log.info("before handshake");
        HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
        HttpServletResponse resp = ((ServletServerHttpResponse) response).getServletResponse();

        // Access Token 추출
        String accessToken = extractAccessToken(request);
        log.info("accessToken: {}", accessToken);

        // 토큰 검증 및 멤버 정보 저장
        if (!verifyTokenAndStoreMemberId(accessToken, attributes, resp)) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, ERROR_MESSAGE);
            log.error("WebSocket connection error: Unauthorized access.");
            return false;
        }

        return true;
    }

    private String extractAccessToken(ServerHttpRequest req) {
        HttpHeaders headers = req.getHeaders();
        String authToken = headers.getFirst(HttpHeaders.AUTHORIZATION);
        if (authToken != null && authToken.startsWith("Bearer ")) {
            return authToken.substring(7);  // Bearer 부분을 제외한 토큰만 추출
        }
        return null;
    }

    private boolean verifyTokenAndStoreMemberId(String accessToken, Map<String, Object> attributes,
                                                HttpServletResponse resp) throws IOException {
        try {
            if (accessToken == null) {
                log.error("Access token is missing.");  // 토큰이 없을 경우 로그 출력
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Access token is missing.");
                return false;  // 토큰이 없으면 연결 실패
            }

            String socialId = jwtProvider.verify(accessToken);  // JWT 토큰 검증
            Member member = memberRepository.findBySocialId(socialId)
                    .orElseThrow(() -> new MemberDuplicateException());  // 회원 조회

            Long memberId = member.getMemberId();
            attributes.put(ChatUtil.MEMBER_ID, memberId);  // WebSocket 세션에 memberId 저장

            return true;
        } catch (MemberNotFoundException e) {
            log.error("Member not found: {}", e.getMessage());  // 회원이 없을 경우 로그 출력
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Member not found.");
        } catch (InvalidTokenException e) {
            log.error("잘못된 토큰: {}", e.getMessage());  // 잘못된 토큰일 경우 로그 출력
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token.");
        } catch (Exception e) {
            log.error("토큰 검증 실패: {}", e.getMessage());  // 토큰 검증 중 일반적인 에러
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token.");
        }
        return false;  // 위의 조건 중 하나라도 실패하면 연결 실패
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception exception) {
        // Handshake 후 처리할 필요가 있을 경우 여기서 처리
    }
}
