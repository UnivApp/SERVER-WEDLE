package yerong.wedle.block.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class AlreadyBlockedMemberException extends CustomException {
    public AlreadyBlockedMemberException() {
        super(ResponseCode.ALREADY_BLOCKED_MEMBER);
    }
}