package yerong.wedle.category.ranking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.category.ranking.domain.Ranking;
import yerong.wedle.category.ranking.domain.RankingCategory;
import yerong.wedle.category.ranking.domain.RankingType;
import yerong.wedle.category.ranking.dto.RankingResponse;
import yerong.wedle.category.ranking.dto.UniversityRankingResponse;
import yerong.wedle.category.ranking.repository.RankingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RankingService {

    private final RankingRepository rankingRepository;

    @Transactional
    public List<RankingResponse> getRankings() {
        List<Ranking> rankings = rankingRepository.findAll();

        List<RankingResponse> rankingResponses = new ArrayList<>();

        List<Ranking> cwtsRankings = rankings.stream()
                .filter(ranking -> ranking.getRankingType() == RankingType.CWTS)
                .collect(Collectors.toList());

        var cwtsGroupedByCategory = cwtsRankings.stream()
                .collect(Collectors.groupingBy(Ranking::getRankingCategory));

        rankingResponses.addAll(rankings.stream()
                .filter(ranking -> ranking.getRankingType() != RankingType.CWTS) // CWTS 제외
                .map(this::convertToRankingResponse)
                .collect(Collectors.toList()));

        for (RankingCategory category : RankingCategory.values()) {
            List<UniversityRankingResponse> universityRankingResponses = cwtsGroupedByCategory.getOrDefault(category, List.of()).stream()
                    .map(this::convertToUniversityRankingResponse)
                    .collect(Collectors.toList());

            if (!universityRankingResponses.isEmpty()) {
                rankingResponses.add(new RankingResponse(
                        RankingType.CWTS.getDisplayName(),
                        RankingType.CWTS.getFullName(),
                        RankingType.CWTS.getDescription(),
                        category.getValue(),
                        universityRankingResponses
                ));
            }
        }

        return rankingResponses;
    }

    private RankingResponse convertToRankingResponse(Ranking ranking) {
        return new RankingResponse(
                ranking.getRankingType().getDisplayName(),
                ranking.getRankingType().getFullName(),
                ranking.getRankingType().getDescription(),
                null,
                List.of(new UniversityRankingResponse(
                        ranking.getUniversityName(),
                        ranking.getLogo(),
                        ranking.getRankNum()
                ))
        );
    }

    private UniversityRankingResponse convertToUniversityRankingResponse(Ranking ranking) {
        return new UniversityRankingResponse(
                ranking.getUniversityName(),
                ranking.getLogo(),
                ranking.getRankNum()
        );
    }
}
