package yerong.wedle.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
