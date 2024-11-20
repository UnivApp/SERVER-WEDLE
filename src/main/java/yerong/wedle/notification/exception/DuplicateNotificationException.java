package yerong.wedle.notification.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class DuplicateNotificationException extends CustomException {
    public DuplicateNotificationException () {
        super(ResponseCode.DUPLICATION_NOTIFICATION);
    }
}
