package yerong.wedle.category.event.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UniversityFestivalResponse {
    private Long universityId;
    private String universityName;
    private List<FestivalResponse> events;
}
