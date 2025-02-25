package yerong.wedle.school.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SchoolRegistrationRequest {
    private String name;
    private String atpt;
    private String schoolCode;
    private String address;
    private String phone;
    private String hompage;
}
