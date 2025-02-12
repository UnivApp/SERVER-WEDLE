package yerong.wedle.like.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class LikeNotFoundException extends CustomException {

    public LikeNotFoundException() {
        super(ResponseCode.LIKE_NOT_FOUND);
    }
}
