package yerong.wedle.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.dto.NicknameRequest;
import yerong.wedle.member.dto.NicknameResponse;
import yerong.wedle.member.exception.ExistingNicknameException;
import yerong.wedle.member.exception.MemberNicknameDuplicateException;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.repository.MemberRepository;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private boolean isNicknameDuplicate(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    public NicknameResponse setNickname(NicknameRequest nicknameRequest) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);

        if (member.getNickname() != null && member.getNickname().equals(nicknameRequest.getNickName())) {
            throw new ExistingNicknameException();
        }

        if (isNicknameDuplicate(nicknameRequest.getNickName())) {
            throw new MemberNicknameDuplicateException();
        }

        member.setNickname(nicknameRequest.getNickName());
        memberRepository.save(member);

        return NicknameResponse.builder()
                .nickName(member.getNickname()).build();
    }


    public NicknameResponse getNickname() {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);

        return NicknameResponse.builder()
                .nickName(member.getNickname()).build();
    }

    private String getCurrentUserId() {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();

        return socialId;
    }
}
