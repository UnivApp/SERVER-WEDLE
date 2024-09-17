package yerong.wedle.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import yerong.wedle.banner.exception.BannerNotFoundException;
import yerong.wedle.category.restaurant.exception.RestaurantNotFoundException;
import yerong.wedle.department.exception.DepartmentNotFoundException;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.exception.MemberDuplicateException;
import yerong.wedle.oauth.exception.InvalidAuthorizationHeaderException;
import yerong.wedle.oauth.exception.InvalidRefreshTokenException;
import yerong.wedle.oauth.exception.InvalidTokenException;
import yerong.wedle.oauth.exception.OAuthProcessingException;
import yerong.wedle.star.exception.StarNotFoundException;
import yerong.wedle.tuitionfee.exception.TuitionFeeNotFoundException;
import yerong.wedle.university.exception.UniversityNotFoundException;
import yerong.wedle.admission.exception.AdmissionNotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getCode(),
                ex.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        HttpStatus status = HttpStatus.valueOf(Integer.parseInt(ex.getCode()));
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMemberNotFoundException(MemberNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.MEMBER_NOT_FOUND.getCode(),
                ResponseCode.MEMBER_NOT_FOUND.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MemberDuplicateException.class)
    public ResponseEntity<ErrorResponse> handleMemberDuplicateException(MemberDuplicateException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.MEMBER_DUPLICATE.getCode(),
                ResponseCode.MEMBER_DUPLICATE.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRefreshTokenException(InvalidRefreshTokenException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.INVALID_REFRESH_TOKEN.getCode(),
                ResponseCode.INVALID_REFRESH_TOKEN.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.INVALID_TOKEN.getCode(),
                ResponseCode.INVALID_TOKEN.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(OAuthProcessingException.class)
    public ResponseEntity<ErrorResponse> handleOAuthProcessingException(OAuthProcessingException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.OAUTH_ERROR.getCode(),
                ResponseCode.OAUTH_ERROR.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    @ExceptionHandler(InvalidAuthorizationHeaderException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAuthorizationHeaderException(InvalidAuthorizationHeaderException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.INVALID_AUTHORIZATION_HEADER.getCode(),
                ResponseCode.INVALID_AUTHORIZATION_HEADER.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    @ExceptionHandler(BannerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBannerNotFoundException(BannerNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.BANNER_NOT_FOUND.getCode(),
                ResponseCode.BANNER_NOT_FOUND.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UniversityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUniversityNotFoundException(UniversityNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.UNIVERSITY_NOT_FOUND.getCode(),
                ResponseCode.UNIVERSITY_NOT_FOUND.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRestaurantNotFoundException(RestaurantNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.RESTAURANT_NOT_FOUND.getCode(),
                ResponseCode.RESTAURANT_NOT_FOUND.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }


    @ExceptionHandler(StarNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStarNotFoundException(StarNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.STAR_NOT_FOUND.getCode(),
                ResponseCode.STAR_NOT_FOUND.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    @ExceptionHandler(AdmissionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAdmissionNotFoundException(AdmissionNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.ADMISSION_NOT_FOUND.getCode(),
                ResponseCode.ADMISSION_NOT_FOUND.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDepartmentNotFoundException(DepartmentNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.DEPARTMENT_NOT_FOUND.getCode(),
                ResponseCode.DEPARTMENT_NOT_FOUND.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    @ExceptionHandler(TuitionFeeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDTuitionFeeNotFoundException(TuitionFeeNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.TUITION_FEE_NOT_FOUND.getCode(),
                ResponseCode.TUITION_FEE_NOT_FOUND.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
                "예상치 못한 오류가 발생했습니다.",
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
