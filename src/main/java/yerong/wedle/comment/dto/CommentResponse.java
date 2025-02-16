package yerong.wedle.comment.dto;

import lombok.Data;

@Data
public class CommentResponse {
    private Long commentId;
    private String content;
    private boolean anonymous;
    private Long likeCount;
    private boolean isLiked;
    private Long parentCommentId;

    public CommentResponse(Long id, String content, boolean anonymous, Long likeCount, boolean isLiked,
                           Long parentCommentId) {
        this.commentId = id;
        this.content = content;
        this.anonymous = anonymous;
        this.likeCount = likeCount;
        this.isLiked = isLiked;
        this.parentCommentId = parentCommentId;
    }
}
