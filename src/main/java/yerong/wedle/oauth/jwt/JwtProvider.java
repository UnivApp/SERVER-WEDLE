package yerong.wedle.oauth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;
import yerong.wedle.member.domain.Role;
import yerong.wedle.oauth.dto.TokenResponse;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;


@Slf4j
@Component
public class JwtProvider {

    private final Key key;
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; //access 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 14; //refresh 7일

    public JwtProvider(@Value("${jwt.secret_key}") String secretKey){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenResponse generateTokenDto(String socialId){
        long now = (new Date()).getTime();

        //Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

        String accessToken = Jwts.builder()
                .setSubject(socialId)
                .claim(AUTHORITIES_KEY, Role.USER.getKey())
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenResponse.builder()
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .build();
    }

    public Authentication getAuthentication(String accessToken){
        Claims claims = parseClaims(accessToken);

        if(claims.get(AUTHORITIES_KEY) == null){
            throw new CustomException(ResponseCode.UNAUTHORIZED);
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);


    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명입니다. {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT 토큰이 만료되었습니다. {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 JWT 토큰입니다. {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다. {}", e.getMessage());
        }
        return false;
    }
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}