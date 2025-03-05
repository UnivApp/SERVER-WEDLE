package yerong.wedle.todo.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class TodoNotFoundException extends CustomException {
    public TodoNotFoundException() {
        super(ResponseCode.TODO_NOT_FOUND);
    }
}
