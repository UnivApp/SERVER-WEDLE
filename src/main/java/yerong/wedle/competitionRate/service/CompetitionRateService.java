package yerong.wedle.competitionRate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.competitionRate.domain.CompetitionRate;
import yerong.wedle.competitionRate.dto.CompetitionRateResponse;
import yerong.wedle.competitionRate.exception.CompetitionRateNotFoundException;
import yerong.wedle.competitionRate.repository.CompetitionRateRepository;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.exception.UniversityNotFoundException;
import yerong.wedle.university.repository.UniversityRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompetitionRateService {

    private final CompetitionRateRepository competitionRateRepository;
    private final UniversityRepository universityRepository;
    @Transactional(readOnly = true)
    public List<CompetitionRateResponse> getLastThreeYearsCompetitionRates(Long universityId) {
        University university = universityRepository.findById(universityId).orElseThrow(UniversityNotFoundException::new);
        List<CompetitionRate> rates = competitionRateRepository.findByUniversity(university);

        return rates.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public CompetitionRateResponse getLatestCompetitionRate(Long universityId) {
        University university = universityRepository.findById(universityId).orElseThrow(UniversityNotFoundException::new);
        CompetitionRate latestRate = competitionRateRepository.findTopByUniversityOrderByCompetitionYearDesc(university).orElseThrow(CompetitionRateNotFoundException::new);

        return convertToDto(latestRate);
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
