package yerong.wedle.employmentRate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yerong.wedle.competitionRate.domain.CompetitionRate;
import yerong.wedle.employmentRate.domain.EmploymentRate;
import yerong.wedle.university.domain.University;

import java.util.List;
import java.util.Optional;

public interface EmploymentRateRepository extends JpaRepository<EmploymentRate, Long> {
    List<EmploymentRate> findByUniversity(University university);

    @Query("SELECT e FROM EmploymentRate e WHERE e.university IS NOT NULL ORDER BY e.university.name ASC")
    List<EmploymentRate> findTop5ByOrderByUniversityNameAsc();
}
