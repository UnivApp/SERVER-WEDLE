package yerong.wedle.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PostUpdateRequest {
    private Long postId;
    private String title;
    private String content;
}
