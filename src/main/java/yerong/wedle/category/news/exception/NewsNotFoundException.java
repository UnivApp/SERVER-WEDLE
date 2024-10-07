package yerong.wedle.category.news.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class NewsNotFoundException extends CustomException {
    public NewsNotFoundException() {
        super(ResponseCode.NEWS_NOT_FOUND);
    }
}
