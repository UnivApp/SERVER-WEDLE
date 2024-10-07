package yerong.wedle.category.ranking.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.category.ranking.domain.RankingType;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.dto.UniversityResponse;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RankingResponse {
    private String displayName;
    private String fullName;
    private String description;
    private int year;
    private String category;
    List<UniversityRankingResponse> universityRankingResponses;

}
