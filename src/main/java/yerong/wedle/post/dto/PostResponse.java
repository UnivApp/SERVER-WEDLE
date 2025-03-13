package yerong.wedle.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostResponse {
    private Long postId;
    private String title;
    private String content;
    @JsonProperty("anonymous")
    private boolean isAnonymous;
    private Long likeCount;
    private boolean isLiked;
    private String username;
}
