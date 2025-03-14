package yerong.wedle.report.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class AlreadyReportedCommentException extends CustomException {
    public AlreadyReportedCommentException() {
        super(ResponseCode.ALREADY_REPORTED_COMMENT);
    }
}