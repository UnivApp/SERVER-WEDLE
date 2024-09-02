package yerong.wedle.star.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import yerong.wedle.star.domain.Star;

public interface StarRepository extends JpaRepository<Star, Long> {

    @Modifying
    @Query(value = "INSERT INTO star(member_id, university_id, createdAt) VALUES (:memberId, :universityId, now())", nativeQuery = true)
    int mStar(Long memberId, Long universityId);

    @Modifying
    @Query(value = "DELETE FROM star WHERE member_id = :memberId AND university_id = :university_id", nativeQuery = true)
    int mUnStar(Long member_id, Long universityId);
}
