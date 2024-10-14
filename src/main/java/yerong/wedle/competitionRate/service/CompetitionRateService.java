package yerong.wedle.competitionRate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.competitionRate.domain.CompetitionRate;
import yerong.wedle.competitionRate.dto.CompetitionRateResponse;
import yerong.wedle.competitionRate.dto.UniversityCompetitionRateResponse;
import yerong.wedle.competitionRate.exception.CompetitionRateNotFoundException;
import yerong.wedle.competitionRate.repository.CompetitionRateRepository;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.exception.UniversityNotFoundException;
import yerong.wedle.university.repository.UniversityRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompetitionRateService {

    private final CompetitionRateRepository competitionRateRepository;
    private final UniversityRepository universityRepository;
    @Transactional(readOnly = true)
    public UniversityCompetitionRateResponse getUniversityCompetitionRates(Long universityId) {
        University university = universityRepository.findById(universityId).orElseThrow(UniversityNotFoundException::new);
        List<CompetitionRate> rates = competitionRateRepository.findByUniversity(university);

        if(rates.isEmpty()){
            throw new CompetitionRateNotFoundException();
        }

        List<CompetitionRateResponse> competitionRateResponses = rates.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return new UniversityCompetitionRateResponse(
                university.getName(),
                university.getLogo(),
                competitionRateResponses
        );
    }

    @Transactional(readOnly = true)
    public List<UniversityCompetitionRateResponse> getTop5UniversitiesCompetitionRates() {
        List<CompetitionRate> top5Rates = competitionRateRepository.findTop5ByOrderByUniversityNameAsc();

        Map<University, List<CompetitionRateResponse>> universityRatesMap = top5Rates.stream()
                .collect(Collectors.groupingBy(
                        CompetitionRate::getUniversity,
                        Collectors.mapping(this::convertToDto, Collectors.toList())
                ));
        return universityRatesMap.entrySet().stream()
                .map(entry -> new UniversityCompetitionRateResponse(
                        entry.getKey().getName(),
                        entry.getKey().getLogo(),
                        entry.getValue()
                ))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UniversityCompetitionRateResponse> getAllUniversitiesCompetitionRates() {

        List<CompetitionRate> rates = competitionRateRepository.findAll();
        Map<University, List<CompetitionRateResponse>> universityRatesMap = rates.stream()
                .collect(Collectors.groupingBy(
                        CompetitionRate::getUniversity,
                        Collectors.mapping(this::convertToDto, Collectors.toList())
                ));
        return universityRatesMap.entrySet().stream()
                .map(entry -> new UniversityCompetitionRateResponse(
                        entry.getKey().getName(),
                        entry.getKey().getLogo(),
                        entry.getValue()
                ))
                .collect(Collectors.toList());
    }
    private CompetitionRateResponse convertToDto(CompetitionRate competitionRate) {
        return new CompetitionRateResponse(
                competitionRate.getEarlyAdmissionRate(),
                competitionRate.getRegularAdmissionRate(),
                competitionRate.getAverageAdmissionRate(),
                competitionRate.getCompetitionYear()
        );
    }
}
