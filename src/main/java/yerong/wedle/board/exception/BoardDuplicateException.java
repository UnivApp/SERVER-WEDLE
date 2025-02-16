package yerong.wedle.board.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class BoardDuplicateException extends CustomException {
    public BoardDuplicateException() {
        super(ResponseCode.BOARD_DUPULICATE);
    }
}
