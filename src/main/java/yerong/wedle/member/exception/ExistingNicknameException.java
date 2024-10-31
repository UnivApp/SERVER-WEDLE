package yerong.wedle.member.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class ExistingNicknameException extends CustomException {

    public ExistingNicknameException() {
        super(ResponseCode.EXISTING_NICKNAME);
    }
}
