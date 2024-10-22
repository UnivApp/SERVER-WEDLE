package yerong.wedle.department.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DepartmentResponse {

    private String type;
    private List<String> name;

}
