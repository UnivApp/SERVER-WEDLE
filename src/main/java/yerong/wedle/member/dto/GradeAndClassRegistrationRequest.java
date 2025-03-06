package yerong.wedle.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GradeAndClassRegistrationRequest {
    private int grade;
    private String className;
}
