package yerong.wedle.like.commentLike.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class CommentLikeNotFoundException extends CustomException {

    public CommentLikeNotFoundException() {
        super(ResponseCode.COMMENT_LIKE_NOT_FOUND);
    }
}
