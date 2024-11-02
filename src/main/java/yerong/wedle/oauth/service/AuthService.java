package yerong.wedle.oauth.service;

import com.nimbusds.jwt.JWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.domain.Role;
import yerong.wedle.member.dto.MemberRequest;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.repository.MemberRepository;
import yerong.wedle.oauth.domain.RefreshToken;
import yerong.wedle.oauth.dto.LoginResponse;
import yerong.wedle.oauth.dto.MemberLogoutResponse;
import yerong.wedle.oauth.dto.TokenResponse;
import yerong.wedle.oauth.exception.InvalidAuthorizationHeaderException;
import yerong.wedle.oauth.exception.InvalidRefreshTokenException;
import yerong.wedle.oauth.jwt.JwtProvider;
import yerong.wedle.oauth.repository.RefreshTokenRepository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final RedisTemplate redisTemplate;
    private final JwtBlacklistService jwtBlacklistService;

    private static final String BEARER = "Bearer ";


    @Transactional
    public LoginResponse login(MemberRequest memberRequest){

        Member member = memberRepository.findBySocialId(memberRequest.getSocialId()).orElse(null);

        if (member == null) {

            member = Member.builder()
                    .socialId(memberRequest.getSocialId())
                    .email(memberRequest.getEmail())
                    .username(memberRequest.getName())
                    .role(Role.USER)
                    .isExistingMember(false)
                    .build();
            memberRepository.save(member);
        }else {
            member.setExistingMember(true);
        }
        TokenResponse tokenResponse = jwtProvider.generateTokenDto(memberRequest.getSocialId());

        Optional<RefreshToken> existingRefreshToken = refreshTokenRepository.findByMemberId(member.getMemberId());
        if (existingRefreshToken.isPresent()) {
            existingRefreshToken.get().update(tokenResponse.getRefreshToken());
            refreshTokenRepository.save(existingRefreshToken.get());
        } else {
            RefreshToken refreshToken = RefreshToken.builder()
                    .memberId(member.getMemberId())
                    .refreshToken(tokenResponse.getRefreshToken())
                    .build();
            refreshTokenRepository.save(refreshToken);
        }
        redisTemplate.opsForValue().set("RT:" + member.getSocialId(), tokenResponse.getRefreshToken(), tokenResponse.getRefreshTokenExpiresIn(), TimeUnit.MILLISECONDS);

        return new LoginResponse(tokenResponse.getAccessToken(), tokenResponse.getRefreshToken(), member.isExistingMember(), hasNickname(member.getSocialId()));
    }
    @Transactional
    public TokenResponse refreshAccessToken(String refreshTokenValue){
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(refreshTokenValue).orElse(null);
        if (refreshToken == null) {
            throw new InvalidRefreshTokenException();
        }

        Member member = memberRepository.findById(refreshToken.getMemberId())
                .orElseThrow(MemberNotFoundException::new);

        TokenResponse tokenResponse = jwtProvider.generateTokenDto(member.getSocialId());

        refreshToken.update(tokenResponse.getRefreshToken());
        refreshTokenRepository.save(refreshToken);

        return tokenResponse;
    }


    public HttpHeaders setTokenHeaders(TokenResponse tokenResponse) {
        HttpHeaders headers = new HttpHeaders();
        ResponseCookie cookie = ResponseCookie.from("RefreshToken", tokenResponse.getRefreshToken())
                .path("/")
                .maxAge(60*60*24*7) // 쿠키 유효기간 7일로 설정
                .secure(true)
                .sameSite("None")
                .httpOnly(true)
                .build();
        headers.add("Set-cookie", cookie.toString());
        headers.add("Authorization", tokenResponse.getAccessToken());

        return headers;
    }

    public boolean isLoggedIn() {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();
        return socialId != null && memberRepository.findBySocialId(socialId).isPresent();
    }

    public boolean hasNickname(String socialId) {
        if(socialId == null) {
            socialId = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        Member member = memberRepository.findBySocialId(socialId).orElse(null);
        return member.getNickname() != null;
    }
    @Transactional
    public MemberLogoutResponse logout(String socialId) {
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);
        RefreshToken refreshToken = refreshTokenRepository.findByMemberId(member.getMemberId())
                .orElseThrow(InvalidRefreshTokenException::new);
        jwtBlacklistService.addTokenToBlacklist(refreshToken.getRefreshToken());
        refreshTokenRepository.delete(refreshToken);
        redisTemplate.delete("RT:" + socialId);

        return new MemberLogoutResponse(member.getSocialId(), refreshToken.getRefreshToken());
    }

    @Transactional
    public void deleteMember() {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();
        if (socialId == null) {
            throw new MemberNotFoundException();
        }
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);

        memberRepository.delete(member);
        refreshTokenRepository.deleteByMemberId(member.getMemberId());
        redisTemplate.delete("RT:" + socialId);

        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByMemberId(member.getMemberId());
        refreshTokenOptional.ifPresent(refreshToken -> jwtBlacklistService.addTokenToBlacklist(refreshToken.getRefreshToken()));

    }

    public boolean isTokenValid(String token) {
        try {
            JWT jwt = jwtProvider.parseToken(token);
            return jwt != null && !jwtBlacklistService.isTokenBlacklisted(token);
        } catch (Exception e) {
            log.error("Token validation error", e);
            return false;
        }
    }

    public String extractAccessTokenFromHeader(String authorizationHeader) {
        return Optional.ofNullable(authorizationHeader)
                .filter(header -> header.startsWith(BEARER))
                .map(header -> header.replace(BEARER, ""))
                .orElseThrow(InvalidAuthorizationHeaderException::new);

    }
}