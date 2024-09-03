package yerong.wedle.category.ranking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.category.ranking.domain.Ranking;
import yerong.wedle.category.ranking.dto.RankingResponse;
import yerong.wedle.category.ranking.repository.RankingRepository;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.repository.UniversityRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RankingService {

    private final RankingRepository rankingRepository;
    private final UniversityRepository universityRepository;

    @Transactional
    public List<RankingResponse> getRankingsByUniversityName(String universityName) {
        University university = universityRepository.findByName(universityName)
                .orElseThrow(() -> new IllegalArgumentException("대학교를 찾을 수 없습니다."));
        List<Ranking> rankings = rankingRepository.findByUniversity(university);
        return rankings.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private RankingResponse convertToDto(Ranking ranking) {
        return new RankingResponse(
                ranking.getRankingType(),
                ranking.getRank(),
                ranking.getReputation(),
                ranking.getSource()
        );
    }
}