package yerong.wedle.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PostUpdateAnonymousRequest {
    private Long postId;

    @JsonProperty("anonymous")
    private boolean isAnonymous = true;
}
