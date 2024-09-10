package yerong.wedle.category.restaurant.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class RestaurantNotFoundException extends CustomException {
    public RestaurantNotFoundException() {
        super(ResponseCode.RESTAURANT_NOT_FOUND);
    }
}
