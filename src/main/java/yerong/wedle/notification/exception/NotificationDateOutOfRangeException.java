package yerong.wedle.notification.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class NotificationDateOutOfRangeException extends CustomException {
    public NotificationDateOutOfRangeException() {
        super(ResponseCode.NOTIFICATION_DATE_OUT_OF_RANGE);
    }
}
