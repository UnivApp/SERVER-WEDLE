package yerong.wedle.common.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import yerong.wedle.board.exception.BoardDuplicateException;
import yerong.wedle.board.exception.BoardNotFoundException;
import yerong.wedle.calendar.exception.CalendarEventNotFoundException;
import yerong.wedle.category.expo.exception.ExpoCategoryNotFoundException;
import yerong.wedle.category.expo.exception.ExpoNotFoundException;
import yerong.wedle.category.news.exception.NewsNotFoundException;
import yerong.wedle.category.questionnaire.exception.MatchingResultNotFoundException;
import yerong.wedle.category.restaurant.exception.RestaurantNotFoundException;
import yerong.wedle.comment.exception.CommentNotFoundException;
import yerong.wedle.competitionRate.exception.CompetitionRateNotFoundException;
import yerong.wedle.department.exception.DepartmentNotFoundException;
import yerong.wedle.employmentRate.exception.EmploymentRateNotFoundException;
import yerong.wedle.like.commentLike.exception.CommentLikeNotFoundException;
import yerong.wedle.like.postLike.exception.PostLikeNotFoundException;
import yerong.wedle.member.exception.ExistingNicknameException;
import yerong.wedle.member.exception.InvalidNicknameException;
import yerong.wedle.member.exception.MemberDuplicateException;
import yerong.wedle.member.exception.MemberNicknameDuplicateException;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.exception.UnauthorizedAccessException;
import yerong.wedle.notification.exception.DuplicateNotificationException;
import yerong.wedle.notification.exception.NotificationDateOutOfRangeException;
import yerong.wedle.notification.exception.NotificationNotFoundException;
import yerong.wedle.oauth.exception.InvalidAuthorizationHeaderException;
import yerong.wedle.oauth.exception.InvalidRefreshTokenException;
import yerong.wedle.oauth.exception.InvalidTokenException;
import yerong.wedle.oauth.exception.OAuthProcessingException;
import yerong.wedle.post.exception.PostNotFoundException;
import yerong.wedle.school.exception.SchoolChangeNotAllowedException;
import yerong.wedle.school.exception.SchoolNotFoundException;
import yerong.wedle.schoolcalendar.exception.SchoolCalendarNotFoundException;
import yerong.wedle.star.exception.StarNotFoundException;
import yerong.wedle.tuitionfee.exception.TuitionFeeNotFoundException;
import yerong.wedle.university.exception.UniversityNotFoundException;

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

    @ExceptionHandler(MemberNicknameDuplicateException.class)
    public ResponseEntity<ErrorResponse> handleMemberNicknameDuplicateException(MemberNicknameDuplicateException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.MEMBER_NICKNAME_DUPLICATE.getCode(),
                ResponseCode.MEMBER_NICKNAME_DUPLICATE.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(InvalidNicknameException.class)
    public ResponseEntity<ErrorResponse> handleInvalidNicknameException(InvalidNicknameException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.NICKNAME_BLANK.getCode(),
                ResponseCode.NICKNAME_BLANK.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotificationNotFoundException(NotificationNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.NOTIFICATION_NOT_FOUND.getCode(),
                ResponseCode.NOTIFICATION_NOT_FOUND.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(DuplicateNotificationException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateNotificationException(DuplicateNotificationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.DUPLICATION_NOTIFICATION.getCode(),
                ResponseCode.DUPLICATION_NOTIFICATION.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(ExistingNicknameException.class)
    public ResponseEntity<ErrorResponse> handleExistingNicknameException(ExistingNicknameException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.EXISTING_NICKNAME.getCode(),
                ResponseCode.EXISTING_NICKNAME.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    @ExceptionHandler(NotificationDateOutOfRangeException.class)
    public ResponseEntity<ErrorResponse> handleNotificationDateOutOfRangeException(
            NotificationDateOutOfRangeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.NOTIFICATION_DATE_OUT_OF_RANGE.getCode(),
                ResponseCode.NOTIFICATION_DATE_OUT_OF_RANGE.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
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

    @ExceptionHandler(ExpoCategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleExpoCategoryNotFoundException(ExpoCategoryNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.EXPO_CATEGORY_NOT_FOUND.getCode(),
                ResponseCode.EXPO_CATEGORY_NOT_FOUND.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ExpoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleExpoNotFoundException(ExpoNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.EXPO_NOT_FOUND.getCode(),
                ResponseCode.EXPO_NOT_FOUND.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(CompetitionRateNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCompetitionRateNotFoundException(CompetitionRateNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.COMPETITION_RATE_NOT_FOUND.getCode(),
                ResponseCode.COMPETITION_RATE_NOT_FOUND.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(EmploymentRateNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEmploymentRateNotFoundException(EmploymentRateNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.EMPLOYMENT_RATE_NOT_FOUND.getCode(),
                ResponseCode.EMPLOYMENT_RATE_NOT_FOUND.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(NewsNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNewsNotFoundException(NewsNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.NEWS_NOT_FOUND.getCode(),
                ResponseCode.NEWS_NOT_FOUND.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(InvalidAuthorizationHeaderException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAuthorizationHeaderException(
            InvalidAuthorizationHeaderException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.INVALID_AUTHORIZATION_HEADER.getCode(),
                ResponseCode.INVALID_AUTHORIZATION_HEADER.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(CalendarEventNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCalendarEventNotFoundException(CalendarEventNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.CALENDAR_EVENT_NOT_FOUND.getCode(),
                ResponseCode.CALENDAR_EVENT_NOT_FOUND.getMessage(),
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

    @ExceptionHandler(MatchingResultNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMatchingResultNotFoundException(MatchingResultNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.MATCHING_RESULT_NOT_FOUND.getCode(),
                ResponseCode.MATCHING_RESULT_NOT_FOUND.getMessage(),
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
    public ResponseEntity<ErrorResponse> handleTuitionFeeNotFoundException(TuitionFeeNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.TUITION_FEE_NOT_FOUND.getCode(),
                ResponseCode.TUITION_FEE_NOT_FOUND.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(SchoolNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSchoolNotFoundException(SchoolNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.SCHOOL_NOT_FOUND.getCode(),
                ResponseCode.SCHOOL_NOT_FOUND.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(SchoolChangeNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleSchoolChangeNotAllowedException(SchoolChangeNotAllowedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.SCHOOL_CHANGE_NOT_ALLOWED.getCode(),
                ResponseCode.SCHOOL_CHANGE_NOT_ALLOWED.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBoardNotFoundException(BoardNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.BOARD_NOT_FOUND.getCode(),
                ResponseCode.BOARD_NOT_FOUND.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(BoardDuplicateException.class)
    public ResponseEntity<ErrorResponse> handleBoardDuplicateException(BoardDuplicateException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.BOARD_DUPULICATE.getCode(),
                ResponseCode.BOARD_DUPULICATE.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFoundException(PostNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.POST_NOT_FOUND.getCode(),
                ResponseCode.POST_NOT_FOUND.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedAccessException(UnauthorizedAccessException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
                ResponseCode.POST_NOT_FOUND.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCommentNotFoundException(CommentNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.COMMENT_NOT_FOUND.getCode(),
                ResponseCode.COMMENT_NOT_FOUND.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(CommentLikeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCommentLikeNotFoundException(CommentLikeNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.COMMENT_LIKE_NOT_FOUND.getCode(),
                ResponseCode.COMMENT_LIKE_NOT_FOUND.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(PostLikeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostLikeNotFoundException(PostLikeNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.POST_LIKE_NOT_FOUND.getCode(),
                ResponseCode.POST_LIKE_NOT_FOUND.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(SchoolCalendarNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSchoolCalendarNotFoundException(SchoolCalendarNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.SCHOOL_CALENDAR_EVENT_NOT_FOUND.getCode(),
                ResponseCode.SCHOOL_CALENDAR_EVENT_NOT_FOUND.getMessage(),
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
