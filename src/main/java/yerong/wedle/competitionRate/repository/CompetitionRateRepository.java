package yerong.wedle.competitionRate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yerong.wedle.competitionRate.domain.CompetitionRate;
import yerong.wedle.university.domain.University;

import java.util.List;
import java.util.Optional;

public interface CompetitionRateRepository extends JpaRepository<CompetitionRate, Long> {
    List<CompetitionRate> findByUniversity(University university);
    @Query("SELECT c FROM CompetitionRate c WHERE c.university IS NOT NULL ORDER BY c.university.name ASC")
    List<CompetitionRate> findTop5ByOrderByUniversityNameAsc();
}
