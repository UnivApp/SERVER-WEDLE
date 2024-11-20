package yerong.wedle.category.event.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FestivalWithArtistsResponse {
    private String day;
    private List<ArtistResponse> lineup;
}
