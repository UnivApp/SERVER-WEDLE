package yerong.wedle.employmentRate.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class EmploymentRateNotFoundException extends CustomException {
    public EmploymentRateNotFoundException() {
        super(ResponseCode.EMPLOYMENT_RATE_NOT_FOUND);
    }
}
