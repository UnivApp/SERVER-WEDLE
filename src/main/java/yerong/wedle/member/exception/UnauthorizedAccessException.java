package yerong.wedle.member.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class UnauthorizedAccessException extends CustomException {
    public UnauthorizedAccessException() {
        super(ResponseCode.UNAUTHORIZED_ACCESS);
    }
}
