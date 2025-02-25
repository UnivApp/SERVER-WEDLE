package yerong.wedle.school.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class SchoolChangeNotAllowedException extends CustomException {
    public SchoolChangeNotAllowedException() {
        super(ResponseCode.SCHOOL_CHANGE_NOT_ALLOWED);
    }
}
