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

    private String name;
    private String location;
    private String logo;
    private Long starNum;
}
