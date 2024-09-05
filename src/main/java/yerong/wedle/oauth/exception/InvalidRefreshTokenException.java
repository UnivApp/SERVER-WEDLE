package yerong.wedle.oauth.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class InvalidRefreshTokenException extends CustomException {
    public InvalidRefreshTokenException() {
        super(ResponseCode.INVALID_REFRESH_TOKEN);
    }
}
