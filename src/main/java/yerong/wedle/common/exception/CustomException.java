package yerong.wedle.common.exception;

public class CustomException extends RuntimeException {
    private final ResponseCode responseCode;

    public CustomException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }

    public String getCode() {
        return responseCode.getCode();
    }
}