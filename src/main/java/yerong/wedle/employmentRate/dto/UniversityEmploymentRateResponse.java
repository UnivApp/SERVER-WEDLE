package yerong.wedle.employmentRate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UniversityEmploymentRateResponse {
    private String name;
    private String logo;
    private List<EmploymentRateResponse> employmentRateResponses;
}
