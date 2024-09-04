package yerong.wedle.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.exception.MemberDuplicateException;
import yerong.wedle.oauth.exception.InvalidRefreshTokenException;
import yerong.wedle.oauth.exception.OAuthProcessingException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException customException) {
        ErrorResponse errorResponse = new ErrorResponse(
                customException.getCode(),
                customException.getMessage(),
                LocalDateTime.now().format(formatter)
        );
        HttpStatus status = HttpStatus.valueOf(Integer.parseInt(customException.getCode()));
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMemberNotFoundException(MemberNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.MEMBER_NOT_FOUND.getCode(),
                ResponseCode.MEMBER_NOT_FOUND.getMessage(),
                LocalDateTime.now().format(formatter)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MemberDuplicateException.class)
    public ResponseEntity<ErrorResponse> handleMemberDuplicateException(MemberDuplicateException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.MEMBER_DUPLICATE.getCode(),
                ResponseCode.MEMBER_DUPLICATE.getMessage(),
                LocalDateTime.now().format(formatter)
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRefreshTokenException(InvalidRefreshTokenException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.INVALID_REFRESH_TOKEN.getCode(),
                ResponseCode.INVALID_REFRESH_TOKEN.getMessage(),
                LocalDateTime.now().format(formatter)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(OAuthProcessingException.class)
    public ResponseEntity<ErrorResponse> handleOAuthProcessingException(OAuthProcessingException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.OAUTH_ERROR.getCode(),
                ResponseCode.OAUTH_ERROR.getMessage(),
                LocalDateTime.now().format(formatter)
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                "예상치 못한 오류가 발생했습니다.",
                LocalDateTime.now().format(formatter)
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}