package yerong.wedle.schoolcalendar.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class SchoolCalendarNotFoundException extends CustomException {
    public SchoolCalendarNotFoundException() {
        super(ResponseCode.SCHOOL_CALENDAR_EVENT_NOT_FOUND);
    }
}
