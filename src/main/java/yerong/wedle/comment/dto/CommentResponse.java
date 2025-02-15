package yerong.wedle.comment.dto;

import lombok.Data;

@Data
public class CommentResponse {
    private Long commentId;
    private String content;
    private boolean anonymous;

    public CommentResponse(Long id, String content, boolean anonymous) {
        this.commentId = id;
        this.content = content;
        this.anonymous = anonymous;
    }
}
