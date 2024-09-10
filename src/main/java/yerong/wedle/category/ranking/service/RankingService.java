package yerong.wedle.category.ranking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.category.ranking.domain.Ranking;
import yerong.wedle.category.ranking.dto.RankingResponse;
import yerong.wedle.category.ranking.repository.RankingRepository;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.exception.UniversityNotFoundException;
import yerong.wedle.university.repository.UniversityRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RankingService {

    private final RankingRepository rankingRepository;
    private final UniversityRepository universityRepository;

    @Transactional
    public List<RankingResponse> getRankingsByUniversityId(Long universityId) {
        University university = universityRepository.findById(universityId)
                .orElseThrow(UniversityNotFoundException::new);
        List<Ranking> rankings = rankingRepository.findByUniversity(university);
        return rankings.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private RankingResponse convertToDto(Ranking ranking) {
        return new RankingResponse(
                ranking.getRankingType().getDisplayName(),
                ranking.getWorldRank(),
                ranking.getAsiaRank(),
                ranking.getDomesticRank()
        );
    }
}