package yerong.wedle.star.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.repository.MemberRepository;
import yerong.wedle.star.domain.Star;
import yerong.wedle.star.exception.StarNotFoundException;
import yerong.wedle.star.repository.StarRepository;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.exception.UniversityNotFoundException;
import yerong.wedle.university.repository.UniversityRepository;

@RequiredArgsConstructor
@Service
@Slf4j
public class StarService {

    private final StarRepository starRepository;
    private final UniversityRepository universityRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void addStar(String universityName) {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);

        University university = universityRepository.findByName(universityName)
                .orElseThrow(UniversityNotFoundException::new);

        boolean alreadyStarred = starRepository.existsByMemberAndUniversity(member, university);
        if (!alreadyStarred) {
            Star star = Star.builder()
                    .member(member)
                    .university(university)
                    .build();
            starRepository.save(star);
        }
    }

    @Transactional
    public void removeStar(String universityName) {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);

        University university = universityRepository.findByName(universityName)
                .orElseThrow(UniversityNotFoundException::new);

        Star star = starRepository.findByMemberAndUniversity(member, university)
                .orElseThrow(StarNotFoundException::new);

        authorizePostMember(star);

        starRepository.delete(star);
    }
    private static void authorizePostMember(Star star) {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();

        if(star.getMember().getSocialId().equals(socialId)){
            throw new CustomException(ResponseCode.FORBIDDEN);
        }
    }
}
