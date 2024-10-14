package yerong.wedle.employmentRate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.competitionRate.domain.CompetitionRate;
import yerong.wedle.competitionRate.dto.CompetitionRateResponse;
import yerong.wedle.competitionRate.dto.UniversityCompetitionRateResponse;
import yerong.wedle.competitionRate.exception.CompetitionRateNotFoundException;
import yerong.wedle.employmentRate.domain.EmploymentRate;
import yerong.wedle.employmentRate.dto.EmploymentRateResponse;
import yerong.wedle.employmentRate.dto.UniversityEmploymentRateResponse;
import yerong.wedle.employmentRate.exception.EmploymentRateNotFoundException;
import yerong.wedle.employmentRate.repository.EmploymentRateRepository;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.exception.UniversityNotFoundException;
import yerong.wedle.university.repository.UniversityRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmploymentRateService {

    private final EmploymentRateRepository employmentRateRepository;
    private final UniversityRepository universityRepository;
    @Transactional(readOnly = true)
    public UniversityEmploymentRateResponse getUniversityEmploymentRates(Long universityId) {
        University university = universityRepository.findById(universityId).orElseThrow(UniversityNotFoundException::new);
        List<EmploymentRate> rates = employmentRateRepository.findByUniversity(university);

        if (rates.isEmpty()) {
            throw new EmploymentRateNotFoundException();
        }

        List<EmploymentRateResponse> employmentRateResponses = rates.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return new UniversityEmploymentRateResponse(
                university.getName(),
                university.getLogo(),
                employmentRateResponses
        );
    }

    @Transactional(readOnly = true)
    public List<UniversityEmploymentRateResponse> getTop5UniversitiesEmploymentRates() {
        List<EmploymentRate> top5Rates = employmentRateRepository.findTop5ByOrderByUniversityNameAsc();

        Map<University, List<EmploymentRateResponse>> universityRatesMap = top5Rates.stream()
                .collect(Collectors.groupingBy(
                        EmploymentRate::getUniversity,
                        Collectors.mapping(this::convertToDto, Collectors.toList())
                ));
        return universityRatesMap.entrySet().stream()
                .map(entry -> new UniversityEmploymentRateResponse(
                        entry.getKey().getName(),
                        entry.getKey().getLogo(),
                        entry.getValue()
                ))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UniversityEmploymentRateResponse> getAllUniversityEmploymentRates() {
        List<EmploymentRate> rates = employmentRateRepository.findAll();

        Map<University, List<EmploymentRateResponse>> universityRatesMap = rates.stream()
                .collect(Collectors.groupingBy(
                        EmploymentRate::getUniversity,
                        Collectors.mapping(this::convertToDto, Collectors.toList())
                ));
        return universityRatesMap.entrySet().stream()
                .map(entry -> new UniversityEmploymentRateResponse(
                        entry.getKey().getName(),
                        entry.getKey().getLogo(),
                        entry.getValue()
                ))
                .collect(Collectors.toList());
    }
    private EmploymentRateResponse convertToDto(EmploymentRate employmentRate) {
        return new EmploymentRateResponse(
                employmentRate.getEmploymentYear(),
                employmentRate.getEmploymentRate()
        );
    }

    public List<UniversityEmploymentRateResponse> getUniversitiesEmploymentRates() {

        List<EmploymentRate> all = employmentRateRepository.findAll();
        return all.stream().map(
                rate -> new UniversityEmploymentRateResponse(
                        rate.getUniversity().getName(),
                        rate.getUniversity().getLogo(),
                        List.of(convertToDto(rate))
                )
        ).collect(Collectors.toList());
    }
}
