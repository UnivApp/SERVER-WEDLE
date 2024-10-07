package yerong.wedle.category.ranking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.category.ranking.domain.Ranking;
import yerong.wedle.category.ranking.domain.RankingType;
import yerong.wedle.category.ranking.dto.RankingResponse;
import yerong.wedle.category.ranking.dto.UniversityRankingResponse;
import yerong.wedle.category.ranking.repository.RankingRepository;
import yerong.wedle.member.repository.MemberRepository;
import yerong.wedle.star.repository.StarRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RankingService {

    private final RankingRepository rankingRepository;
    @Transactional
    public List<RankingResponse> getRankings() {
        List<Ranking> rankings = rankingRepository.findAll();
        return rankings.stream()
                .collect(Collectors.groupingBy(Ranking::getRankingType))
                .entrySet().stream()
                .map(entry->{
                    RankingType rankingType = entry.getKey();
                    List<Ranking> rankingList = entry.getValue();

                    List<UniversityRankingResponse> universityRankingResponses = rankingList.stream()
                            .map(this::convertToUniversityRankingResponse)
                            .collect(Collectors.toList());

                    return new RankingResponse(
                            rankingType.getDisplayName(),
                            rankingType.getFullName(),
                            rankingType.getDescription(),
                            universityRankingResponses
                    );
                })
                .collect(Collectors.toList());
    }

    private UniversityRankingResponse convertToUniversityRankingResponse(Ranking ranking) {
        return new UniversityRankingResponse(
                ranking.getUniversityName(),
                ranking.getLogo(),
                ranking.getRankNum()
        );

    }
}