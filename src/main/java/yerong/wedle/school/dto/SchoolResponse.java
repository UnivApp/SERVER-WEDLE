package yerong.wedle.school.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolResponse {
    private String schoolName;
    private String atptCode;
    private String schoolCode;
    private String roadAddress;
    private String phoneNumber;
    private String website;
}
