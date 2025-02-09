package yerong.wedle.school.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class SchoolNotFoundException extends CustomException {

    public SchoolNotFoundException() {
        super(ResponseCode.SCHOOL_NOT_FOUND);
    }
}
