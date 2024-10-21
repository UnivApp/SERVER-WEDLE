package yerong.wedle.category.questionnaire.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class MatchingResultNotFoundException extends CustomException {
    public MatchingResultNotFoundException() {
        super(ResponseCode.MATCHING_RESULT_NOT_FOUND);
    }
}
