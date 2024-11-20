package yerong.wedle.member.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class InvalidNicknameException extends CustomException {
    public InvalidNicknameException() {
        super(ResponseCode.NICKNAME_BLANK);
    }

}
