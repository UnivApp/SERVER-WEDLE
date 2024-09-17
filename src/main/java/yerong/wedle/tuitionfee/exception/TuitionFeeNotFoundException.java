package yerong.wedle.tuitionfee.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class TuitionFeeNotFoundException extends CustomException {
    public TuitionFeeNotFoundException() {
        super(ResponseCode.TUITION_FEE_NOT_FOUND);
    }
}
