package yerong.wedle.entranceScore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EntranceScoreResponse {

    private String type;
    private byte[] image;
}
