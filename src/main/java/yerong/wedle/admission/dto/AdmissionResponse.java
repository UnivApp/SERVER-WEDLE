package yerong.wedle.admission.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdmissionResponse {
    private String universityName;
    private String universityLocation;
    private String admissionType;
    private boolean isFavorite;
    private double competitionRate;
    private double employmentRate;
    private int recruitmentNumber;
    private int applicantNumber;
}
