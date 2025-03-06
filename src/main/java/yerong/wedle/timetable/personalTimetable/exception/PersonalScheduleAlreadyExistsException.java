package yerong.wedle.timetable.personalTimetable.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class PersonalScheduleAlreadyExistsException extends CustomException {
    public PersonalScheduleAlreadyExistsException() {
        super(ResponseCode.PERSONAL_SCHEDULE_ALREADY_EXISTS);
    }
}
