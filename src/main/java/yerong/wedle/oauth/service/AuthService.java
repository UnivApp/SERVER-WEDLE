package yerong.wedle.oauth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.domain.Role;
import yerong.wedle.member.dto.MemberRequest;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.repository.MemberRepository;
import yerong.wedle.oauth.domain.RefreshToken;
import yerong.wedle.oauth.dto.TokenResponse;
import yerong.wedle.oauth.exception.InvalidRefreshTokenException;
import yerong.wedle.oauth.jwt.JwtProvider;
import yerong.wedle.oauth.repository.RefreshTokenRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public TokenResponse login(MemberRequest memberRequest){

        Member member = memberRepository.findBySocialId(memberRequest.getSocialId()).orElse(null);

        if(member == null){
            member = Member.builder()
                    .socialId(member.getSocialId())
                    .email(member.getEmail())
                    .role(Role.USER)
                    .build();
            memberRepository.save(member);
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
        return tokenResponse;
    }
    @Transactional
    public TokenResponse refreshAccessToken(String refreshTokenValue){
        // RefreshToken을 이용하여 Member 조회
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(refreshTokenValue).orElse(null);
        if (refreshToken == null) {
            throw new InvalidRefreshTokenException();
        }

        Member member = memberRepository.findById(refreshToken.getMemberId())
                .orElseThrow(MemberNotFoundException::new);

        // 새로운 AccessToken 발급
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
}
