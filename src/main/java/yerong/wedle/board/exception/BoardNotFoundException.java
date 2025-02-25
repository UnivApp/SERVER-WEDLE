package yerong.wedle.board.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class BoardNotFoundException extends CustomException {

    public BoardNotFoundException() {
        super(ResponseCode.BOARD_NOT_FOUND);
    }
}
