package yerong.wedle.category.ranking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.category.ranking.domain.Ranking;
import yerong.wedle.university.domain.University;

import java.util.List;

public interface RankingRepository extends JpaRepository<Ranking, Long> {
    List<Ranking> findByUniversity(University university);
}
