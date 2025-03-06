package yerong.wedle.timetable.personalTimetable.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class PersonalTimetableNotFoundException extends CustomException {
    public PersonalTimetableNotFoundException() {
        super(ResponseCode.PERSONAL_TIMETABLE_NOT_FOUND);
    }
}
