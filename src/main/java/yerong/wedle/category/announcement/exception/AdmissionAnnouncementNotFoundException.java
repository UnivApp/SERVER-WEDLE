package yerong.wedle.category.announcement.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class AdmissionAnnouncementNotFoundException extends CustomException {
    public AdmissionAnnouncementNotFoundException() {
        super(ResponseCode.ADMISSION_ANNOUNCEMENT_NOT_FOUND);
    }
}
