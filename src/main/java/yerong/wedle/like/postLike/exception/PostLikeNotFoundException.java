package yerong.wedle.like.postLike.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class PostLikeNotFoundException extends CustomException {

    public PostLikeNotFoundException() {
        super(ResponseCode.POST_LIKE_NOT_FOUND);
    }
}
