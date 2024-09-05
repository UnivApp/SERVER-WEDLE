package yerong.wedle.category.ranking.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.category.ranking.domain.RankingType;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RankingResponse {
    private String rankingType;
    private String worldRank;
    private String asiaRank;
    private String domesticRank;}
