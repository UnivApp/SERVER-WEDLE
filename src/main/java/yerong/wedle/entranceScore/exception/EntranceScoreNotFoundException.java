package yerong.wedle.entranceScore.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class EntranceScoreNotFoundException extends CustomException {
    public EntranceScoreNotFoundException() {
        super(ResponseCode.ENTRANCE_SCORE_NOT_FOUND);
    }
}
