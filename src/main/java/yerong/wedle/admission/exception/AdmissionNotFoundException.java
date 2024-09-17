package yerong.wedle.admission.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class AdmissionNotFoundException extends CustomException {
    public AdmissionNotFoundException() {
        super(ResponseCode.ADMISSION_NOT_FOUND);
    }
}
