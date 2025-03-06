package yerong.wedle.timetable.schoolTimetable.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class SchoolScheduleAlreadyExistsException extends CustomException {
    public SchoolScheduleAlreadyExistsException() {
        super(ResponseCode.SCHOOL_SCHEDULE_ALREADY_EXISTS);
    }
}
