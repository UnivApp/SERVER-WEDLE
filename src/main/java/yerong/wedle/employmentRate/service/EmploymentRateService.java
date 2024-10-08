package yerong.wedle.employmentRate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.competitionRate.domain.CompetitionRate;
import yerong.wedle.competitionRate.dto.CompetitionRateResponse;
import yerong.wedle.competitionRate.exception.CompetitionRateNotFoundException;
import yerong.wedle.employmentRate.domain.EmploymentRate;
import yerong.wedle.employmentRate.dto.EmploymentRateResponse;
import yerong.wedle.employmentRate.exception.EmploymentRateNotFoundException;
import yerong.wedle.employmentRate.repository.EmploymentRateRepository;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.exception.UniversityNotFoundException;
import yerong.wedle.university.repository.UniversityRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmploymentRateService {

    private final EmploymentRateRepository employmentRateRepository;
    private final UniversityRepository universityRepository;
    @Transactional(readOnly = true)
    public List<EmploymentRateResponse> getLastThreeYearsEmploymentRates(Long universityId) {
        University university = universityRepository.findById(universityId).orElseThrow(UniversityNotFoundException::new);
        List<EmploymentRate> rates = employmentRateRepository.findByUniversity(university);

        return rates.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public EmploymentRateResponse getLatestEmploymentRate(Long universityId) {
        University university = universityRepository.findById(universityId).orElseThrow(UniversityNotFoundException::new);
        EmploymentRate latestRate = employmentRateRepository.findTopByUniversityOrderByEmploymentYearDesc(university).orElseThrow(EmploymentRateNotFoundException::new);

        return convertToDto(latestRate);
    }
    private EmploymentRateResponse convertToDto(EmploymentRate employmentRate) {
        return new EmploymentRateResponse(
                employmentRate.getEmploymentRate(),
                employmentRate.getEmploymentYear()
        );
    }
}
