package yerong.wedle.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.community.domain.Community;
import yerong.wedle.school.domain.School;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    boolean existsBySchool(School school);
}
