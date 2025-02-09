package yerong.wedle.school.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SchoolResponse {
    private Long schoolId;
    private String name;
    private String address;
}
