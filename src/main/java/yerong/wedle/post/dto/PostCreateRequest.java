package yerong.wedle.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PostCreateRequest {
    private Long boardId;
    private String title;
    private String content;

    @JsonProperty("anonymous")
    private boolean isAnonymous;
}
