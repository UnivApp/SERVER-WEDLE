package yerong.wedle.comment.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class CommentNotFoundException extends CustomException {

    public CommentNotFoundException() {
        super(ResponseCode.COMMENT_NOT_FOUND);
    }
}
