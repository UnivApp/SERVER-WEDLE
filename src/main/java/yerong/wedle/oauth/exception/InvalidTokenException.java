package yerong.wedle.oauth.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class InvalidTokenException extends CustomException {
    public InvalidTokenException() {
        super(ResponseCode.INVALID_TOKEN);
    }
}
