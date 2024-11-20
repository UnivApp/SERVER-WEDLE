package yerong.wedle.category.event.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FestivalResponse {
    private String eventName;
    private Integer year;
    private String date;
    private List<FestivalWithArtistsResponse> dayLineup;
}
