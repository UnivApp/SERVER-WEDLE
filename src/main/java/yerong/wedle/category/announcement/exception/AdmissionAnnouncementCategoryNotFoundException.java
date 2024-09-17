package yerong.wedle.category.announcement.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class AdmissionAnnouncementCategoryNotFoundException extends CustomException {
    public AdmissionAnnouncementCategoryNotFoundException() {
        super(ResponseCode.ADMISSION_ANNOUNCEMENT_NOT_FOUND);
    }
}
