package yerong.wedle.member.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class MemberNotFoundException extends CustomException {

    public MemberNotFoundException() {
        super(ResponseCode.MEMBER_NOT_FOUND);
    }
}
