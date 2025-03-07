package yerong.wedle.common.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import yerong.wedle.board.exception.BoardDuplicateException;
import yerong.wedle.board.exception.BoardNotFoundException;
import yerong.wedle.comment.exception.CommentNotFoundException;
import yerong.wedle.like.commentLike.exception.CommentLikeNotFoundException;
import yerong.wedle.like.postLike.exception.PostLikeNotFoundException;
import yerong.wedle.member.exception.ExistingNicknameException;
import yerong.wedle.member.exception.InvalidNicknameException;
import yerong.wedle.member.exception.MemberDuplicateException;
import yerong.wedle.member.exception.MemberNicknameDuplicateException;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.exception.UnauthorizedAccessException;
import yerong.wedle.oauth.exception.InvalidAuthorizationHeaderException;
import yerong.wedle.oauth.exception.InvalidRefreshTokenException;
import yerong.wedle.oauth.exception.InvalidTokenException;
import yerong.wedle.oauth.exception.OAuthProcessingException;
import yerong.wedle.post.exception.PostNotFoundException;
import yerong.wedle.school.exception.SchoolChangeNotAllowedException;
import yerong.wedle.school.exception.SchoolNotFoundException;
import yerong.wedle.schoolcalendar.exception.SchoolCalendarNotFoundException;
import yerong.wedle.timetable.personalTimetable.exception.PersonalScheduleAlreadyExistsException;
import yerong.wedle.timetable.personalTimetable.exception.PersonalScheduleNotFoundException;
import yerong.wedle.timetable.personalTimetable.exception.PersonalTimetableNotFoundException;
import yerong.wedle.timetable.schoolTimetable.exception.SchoolScheduleAlreadyExistsException;
import yerong.wedle.timetable.schoolTimetable.exception.SchoolScheduleNotFoundException;
import yerong.wedle.timetable.schoolTimetable.exception.SchoolTimetableNotFoundException;
import yerong.wedle.todo.exception.TodoNotFoundException;

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

    @ExceptionHandler(ExistingNicknameException.class)
    public ResponseEntity<ErrorResponse> handleExistingNicknameException(ExistingNicknameException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.EXISTING_NICKNAME.getCode(),
                ResponseCode.EXISTING_NICKNAME.getMessage(),
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

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTodoNotFoundException(TodoNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.TODO_NOT_FOUND.getCode(),
                ResponseCode.TODO_NOT_FOUND.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(SchoolTimetableNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSchoolTimetableNotFoundException(SchoolTimetableNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.SCHOOL_TIMETABLE_NOT_FOUND.getCode(),
                ResponseCode.SCHOOL_TIMETABLE_NOT_FOUND.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(SchoolScheduleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSchoolScheduleNotFoundException(SchoolScheduleNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.SCHOOL_SCHEDULE_NOT_FOUND.getCode(),
                ResponseCode.SCHOOL_TIMETABLE_NOT_FOUND.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(PersonalTimetableNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePersonalTimetableNotFoundException(
            PersonalTimetableNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.PERSONAL_TIMETABLE_NOT_FOUND.getCode(),
                ResponseCode.PERSONAL_TIMETABLE_NOT_FOUND.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(PersonalScheduleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePersonalScheduleNotFoundException(PersonalScheduleNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.PERSONAL_SCHEDULE_NOT_FOUND.getCode(),
                ResponseCode.PERSONAL_SCHEDULE_NOT_FOUND.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(SchoolScheduleAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleSchoolScheduleAlreadyExistsException(
            SchoolScheduleAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.SCHOOL_SCHEDULE_ALREADY_EXISTS.getCode(),
                ResponseCode.SCHOOL_SCHEDULE_ALREADY_EXISTS.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(PersonalScheduleAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handlePersonalScheduleAlreadyExistsException(
            PersonalScheduleAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ResponseCode.PERSONAL_SCHEDULE_ALREADY_EXISTS.getCode(),
                ResponseCode.PERSONAL_SCHEDULE_ALREADY_EXISTS.getMessage(),
                LocalDateTime.now().format(FORMATTER)
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
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
