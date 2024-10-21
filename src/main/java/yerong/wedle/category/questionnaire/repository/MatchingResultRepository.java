package yerong.wedle.category.questionnaire.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.category.questionnaire.domain.MatchingResult;
import yerong.wedle.category.questionnaire.domain.MatchingResultType;
import yerong.wedle.category.questionnaire.domain.Questionnaire;

public interface MatchingResultRepository extends JpaRepository<MatchingResult, Long> {
    Optional<MatchingResult> findByMatchingResultType(MatchingResultType matchingResultType);
}
