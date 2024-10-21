package yerong.wedle.category.questionnaire.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MatchingResultResponse {
    private Long matchingResultId;
    private String matchingResultType;
    private String recommand;
    private String description;
}
