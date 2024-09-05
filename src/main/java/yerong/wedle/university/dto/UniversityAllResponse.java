package yerong.wedle.university.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UniversityAllResponse {

    private String fullName;
    private String location;
    private String type; //국립/사립
    private String logo;
    private String phoneNumber;
    private String website;
    private Long starNum;
}
