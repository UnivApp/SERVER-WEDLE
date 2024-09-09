package yerong.wedle.oauth.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class InvalidAuthorizationHeaderException extends CustomException {
    public InvalidAuthorizationHeaderException() {
        super(ResponseCode.INVALID_AUTHORIZATION_HEADER);
    }
}
