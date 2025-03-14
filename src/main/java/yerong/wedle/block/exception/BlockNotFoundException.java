package yerong.wedle.block.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class BlockNotFoundException extends CustomException {

    public BlockNotFoundException() {
        super(ResponseCode.BLOCK_NOT_FOUND);
    }
}
