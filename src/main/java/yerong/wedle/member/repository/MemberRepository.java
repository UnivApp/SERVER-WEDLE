package yerong.wedle.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.member.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findBySocialId(String socialId);

    Optional<Member> findByNickname(String nickname);

    boolean existsByNickname(String nickname);
}
