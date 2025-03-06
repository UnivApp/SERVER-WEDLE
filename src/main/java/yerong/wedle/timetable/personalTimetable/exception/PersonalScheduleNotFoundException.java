package yerong.wedle.timetable.personalTimetable.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class PersonalScheduleNotFoundException extends CustomException {
    public PersonalScheduleNotFoundException() {
        super(ResponseCode.PERSONAL_SCHEDULE_NOT_FOUND);
    }
}
