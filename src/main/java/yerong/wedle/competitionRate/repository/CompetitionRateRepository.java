package yerong.wedle.competitionRate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.competitionRate.domain.CompetitionRate;
import yerong.wedle.university.domain.University;

import java.util.List;
import java.util.Optional;

public interface CompetitionRateRepository extends JpaRepository<CompetitionRate, Long> {
    List<CompetitionRate> findByUniversity(University university);

    Optional<CompetitionRate> findTopByUniversityOrderByCompetitionYearDesc(University university);
}
