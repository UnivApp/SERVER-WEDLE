package yerong.wedle.member.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class UserBannedException extends CustomException {
    public UserBannedException() {
        super(ResponseCode.USER_BANNED);
    }
}
