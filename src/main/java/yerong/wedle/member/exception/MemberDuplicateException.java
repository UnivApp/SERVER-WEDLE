package yerong.wedle.member.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class MemberDuplicateException extends CustomException {

    public MemberDuplicateException() {
        super(ResponseCode.MEMBER_DUPLICATE);
    }
}
