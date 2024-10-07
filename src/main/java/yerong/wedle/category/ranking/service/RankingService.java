package yerong.wedle.category.ranking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.category.ranking.domain.Ranking;
import yerong.wedle.category.ranking.dto.RankingResponse;
import yerong.wedle.category.ranking.dto.UniversityRankingResponse;
import yerong.wedle.category.ranking.repository.RankingRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RankingService {

    private final RankingRepository rankingRepository;

    @Transactional(readOnly = true)
    public List<RankingResponse> getAllRankings() {
        List<Ranking> rankings = rankingRepository.findAll();

        return rankings.stream()
                .map(this::convertToRankingResponse)
                .collect(Collectors.toList());
    }

    private RankingResponse convertToRankingResponse(Ranking ranking) {

        UniversityRankingResponse universityRankingResponse = new UniversityRankingResponse(
                ranking.getUniversityName(),
                ranking.getLogo(),
                ranking.getRankNum()
        );


        return new RankingResponse(
                ranking.getRankingType().getDisplayName(),
                ranking.getRankingType().getFullName(),
                ranking.getRankingType().getDescription(),
                ranking.getRankingYear(),
                ranking.getRankingCategory() != null ? ranking.getRankingCategory().name() : null,
                List.of(universityRankingResponse)
        );
    }
}
