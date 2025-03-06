package yerong.wedle.timetable.schoolTimetable.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class SchoolTimetableNotFoundException extends CustomException {
    public SchoolTimetableNotFoundException() {
        super(ResponseCode.SCHOOL_TIMETABLE_NOT_FOUND);
    }
}
