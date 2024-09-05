package yerong.wedle.star.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class StarNotFoundException extends CustomException {
    public StarNotFoundException() {
        super(ResponseCode.STAR_NOT_FOUND);
    }
}
