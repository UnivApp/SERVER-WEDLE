package yerong.wedle.competitionRate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionRateResponse {
    private Double earlyAdmissionRate;
    private Double regularAdmissionRate;
    private Double AverageAdmissionRate;
    private String year;
}
