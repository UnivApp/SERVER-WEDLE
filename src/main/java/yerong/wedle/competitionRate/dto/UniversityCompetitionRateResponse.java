package yerong.wedle.competitionRate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UniversityCompetitionRateResponse {
    private String name;
    private String logo;
    private List<CompetitionRateResponse> competitionRateResponses;

}
