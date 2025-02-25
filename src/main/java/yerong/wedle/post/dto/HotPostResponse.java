package yerong.wedle.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HotPostResponse {
    private Long postId;
    private String title;
    private String content;
    private boolean isAnonymous;
    private Long likeCount;
    private boolean isLiked;
    private String type;
}
