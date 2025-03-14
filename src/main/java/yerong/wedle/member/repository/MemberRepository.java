package yerong.wedle.member.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yerong.wedle.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findBySocialId(String socialId);

    Optional<Member> findByNickname(String nickname);

    boolean existsByNickname(String nickname);

    @Query("SELECT m FROM Member m WHERE m.isBanned = true")
    List<Member> findAllBannedMembers();
}
