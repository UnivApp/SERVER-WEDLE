package yerong.wedle.entranceScore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.entranceScore.domain.EntranceScore;

import java.util.Optional;

public interface EntranceScoreRepository extends JpaRepository<EntranceScore, Long> {
    Optional<EntranceScore> findByType(String type);
}
