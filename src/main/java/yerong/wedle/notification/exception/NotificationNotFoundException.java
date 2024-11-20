package yerong.wedle.notification.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class NotificationNotFoundException extends CustomException {

    public NotificationNotFoundException() {
        super(ResponseCode.MEMBER_NOT_FOUND);
    }
}
