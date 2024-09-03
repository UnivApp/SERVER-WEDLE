package yerong.wedle.star.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.repository.MemberRepository;
import yerong.wedle.star.domain.Star;
import yerong.wedle.star.repository.StarRepository;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.repository.UniversityRepository;

@RequiredArgsConstructor
@Service
public class StarService {

    private final StarRepository starRepository;
    private final UniversityRepository universityRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void addStar(Long memberId, String universityName) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        University university = universityRepository.findByName(universityName).orElseThrow(() -> new IllegalArgumentException("대학을 찾을 수 없습니다."));

        boolean alreadyStarred = starRepository.existsByMemberAndUniversity(member, university);
        if(!alreadyStarred) {
            Star star = Star.builder()
                    .member(member)
                    .university(university)
                    .build();
            starRepository.save(star);
        }
    }

    @Transactional
    public void removeStar(Long memberId, String universityName) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        University university = universityRepository.findByName(universityName).orElseThrow(() -> new IllegalArgumentException("대학을 찾을 수 없습니다."));

        starRepository.deleteByMemberAndUniversity(member, university);
    }
}
