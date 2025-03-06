package yerong.wedle.timetable.schoolTimetable.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class SchoolScheduleNotFoundException extends CustomException {
    public SchoolScheduleNotFoundException() {
        super(ResponseCode.SCHOOL_SCHEDULE_NOT_FOUND);
    }
}
