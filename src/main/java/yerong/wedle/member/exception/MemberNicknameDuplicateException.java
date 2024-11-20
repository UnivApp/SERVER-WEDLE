package yerong.wedle.member.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class MemberNicknameDuplicateException extends CustomException {

    public MemberNicknameDuplicateException() {
        super(ResponseCode.MEMBER_DUPLICATE);
    }
}
