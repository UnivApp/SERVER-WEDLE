package yerong.wedle.department.exception;

import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;

public class DepartmentNotFoundException extends CustomException {
    public DepartmentNotFoundException() {
        super(ResponseCode.DEPARTMENT_NOT_FOUND);
    }
}
