package yerong.wedle.university.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class UniversityNotFoundException extends CustomException {
    public UniversityNotFoundException() {
        super(ResponseCode.UNIVERSITY_NOT_FOUND);
    }
}
