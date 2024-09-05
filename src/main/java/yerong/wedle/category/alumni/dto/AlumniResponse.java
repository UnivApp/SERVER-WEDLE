package yerong.wedle.category.alumni.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.category.alumni.domain.AlumniCategory;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AlumniResponse {

    private String name;
    private String degree;
    private String department;
    private String achievements;
    private AlumniCategory alumniCategory;
    private String imageUrl;
}
