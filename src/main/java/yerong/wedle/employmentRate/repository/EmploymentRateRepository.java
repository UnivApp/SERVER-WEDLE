package yerong.wedle.employmentRate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.competitionRate.domain.CompetitionRate;
import yerong.wedle.employmentRate.domain.EmploymentRate;
import yerong.wedle.university.domain.University;

import java.util.List;
import java.util.Optional;

public interface EmploymentRateRepository extends JpaRepository<CompetitionRate, Long> {
    List<EmploymentRate> findByUniversity(University university);

    Optional<EmploymentRate> findTopByUniversityOrderByEmploymentYearDesc(University university);
}
