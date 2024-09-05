package yerong.wedle.oauth.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class OAuthProcessingException extends CustomException {

    public OAuthProcessingException() {
        super(ResponseCode.OAUTH_ERROR);
    }
}
