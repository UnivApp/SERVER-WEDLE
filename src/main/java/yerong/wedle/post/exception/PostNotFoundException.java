package yerong.wedle.post.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class PostNotFoundException extends CustomException {

    public PostNotFoundException() {
        super(ResponseCode.POST_NOT_FOUND);
    }
}
