package yerong.wedle.category.expo.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class ExpoCategoryNotFoundException extends CustomException {
    public ExpoCategoryNotFoundException() {
        super(ResponseCode.EXPO_CATEGORY_NOT_FOUND);
    }
}
