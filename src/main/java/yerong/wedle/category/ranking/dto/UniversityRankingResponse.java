package yerong.wedle.category.ranking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UniversityRankingResponse {

    private String universityName;
    private String logo;
    private int rank;
}
