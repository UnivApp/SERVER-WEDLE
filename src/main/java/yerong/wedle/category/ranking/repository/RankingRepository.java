package yerong.wedle.category.ranking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.category.activity.domain.Activity;
import yerong.wedle.category.ranking.domain.Ranking;

public interface RankingRepository extends JpaRepository<Ranking, Long> {
}
