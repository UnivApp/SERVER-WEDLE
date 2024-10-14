package yerong.wedle.category.expo.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class ExpoNotFoundException extends CustomException {
    public ExpoNotFoundException() {
        super(ResponseCode.EXPO_NOT_FOUND);
    }
}
