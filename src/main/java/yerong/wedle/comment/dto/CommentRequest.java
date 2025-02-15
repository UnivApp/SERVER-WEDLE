package yerong.wedle.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommentRequest {
    private Long postId;
    private String content;

    @JsonProperty("anonymous")
    private boolean isAnonymous;

    private Long parentCommentId;
}
