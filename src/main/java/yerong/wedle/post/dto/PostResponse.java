package yerong.wedle.post.dto;

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
    private boolean isAnonymous;
}
