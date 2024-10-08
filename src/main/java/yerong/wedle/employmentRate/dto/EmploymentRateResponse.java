package yerong.wedle.employmentRate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmploymentRateResponse {
    private Double employmentRate;
    private String year;
}
