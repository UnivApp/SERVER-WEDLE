package yerong.wedle.competitionRate.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class CompetitionRateNotFoundException extends CustomException {
    public CompetitionRateNotFoundException() {
        super(ResponseCode.COMPETITION_RATE_NOT_FOUND);
    }
}
