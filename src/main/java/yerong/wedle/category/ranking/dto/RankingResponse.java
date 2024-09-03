package yerong.wedle.category.ranking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.category.ranking.domain.RankingType;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RankingResponse {
    private RankingType rankingType;
    private Integer rank;
    private String reputation;
    private String source;
}
