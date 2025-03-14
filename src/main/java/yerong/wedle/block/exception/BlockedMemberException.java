package yerong.wedle.block.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class BlockedMemberException extends CustomException {
    public BlockedMemberException() {
        super(ResponseCode.BLOCKED_MEMBER);
    }
}