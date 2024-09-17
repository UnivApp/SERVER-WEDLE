package yerong.wedle.calendar.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class CalendarEventNotFoundException extends CustomException {
    public CalendarEventNotFoundException() {
        super(ResponseCode.CALENDAR_EVENT_NOT_FOUND);
    }
}
