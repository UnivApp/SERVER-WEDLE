package yerong.wedle.banner.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class BannerNotFoundException extends CustomException {
    public BannerNotFoundException() {
        super(ResponseCode.BANNER_NOT_FOUND);
    }
}
