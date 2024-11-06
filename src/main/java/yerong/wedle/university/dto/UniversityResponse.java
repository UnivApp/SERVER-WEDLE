package yerong.wedle.university.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UniversityResponse {

    private Long universityId;
    private String fullName;
    private String logo;
    private Long starNum;
    private boolean isStarred;
}
