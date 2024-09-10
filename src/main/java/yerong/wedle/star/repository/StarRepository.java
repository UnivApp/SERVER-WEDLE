package yerong.wedle.star.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import yerong.wedle.member.domain.Member;
import yerong.wedle.star.domain.Star;
import yerong.wedle.university.domain.University;

import java.util.List;
import java.util.Optional;

public interface StarRepository extends JpaRepository<Star, Long> {
    @Query("SELECT COUNT(s) FROM Star s WHERE s.university.universityId = :universityId")
    Long countByUniversityId(@Param("universityId") Long universityId);

    @Modifying
    @Query(value = "INSERT INTO star(member_id, university_id, createdAt) VALUES (:memberId, :universityId, now())", nativeQuery = true)
    int mStar(@Param("memberId") Long memberId, @Param("universityId") Long universityId);

    @Modifying
    @Query(value = "DELETE FROM star WHERE member_id = :memberId AND university_id = :universityId", nativeQuery = true)
    int mUnStar(@Param("memberId") Long memberId, @Param("universityId") Long universityId);

    boolean existsByMemberAndUniversity(Member member, University university);

    void deleteByMemberAndUniversity(Member member, University university);

    Optional<Star> findByMemberAndUniversity(Member member, University university);

    List<Star> findByMember(Member member);

    Long countByUniversity(University university);

    boolean existsByUniversityIdAndMemberId(Long universityId, Long memberId);
}
