package yerong.wedle.report.exception;

import static yerong.wedle.common.exception.ResponseCode.ALREADY_REPORTED_POST;

import yerong.wedle.common.exception.CustomException;

public class AlreadyReportedPostException extends CustomException {
    public AlreadyReportedPostException() {
        super(ALREADY_REPORTED_POST);
    }
}