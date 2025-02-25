package yerong.wedle.common.exception;

public enum ResponseCode {
    INVALID_REQUEST("400", "잘못된 요청입니다."),
    UNAUTHORIZED("401", "인증이 필요합니다."),
    FORBIDDEN("403", "접근이 거부되었습니다."),
    NOT_FOUND("404", "정보를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR("500", "서버 오류가 발생했습니다."),

    // Member
    MEMBER_NOT_FOUND("404", "회원이 존재하지 않습니다."),
    MEMBER_DUPLICATE("409", "이미 존재하는 회원입니다."),
    MEMBER_NICKNAME_DUPLICATE("409", "이미 존재하는 닉네임입니다."),
    EXISTING_NICKNAME("400", "기존 닉네임과 동일합니다."),
    NICKNAME_BLANK("400", "닉네임에 공백을 포함할 수 없습니다."),
    // Notification
    NOTIFICATION_NOT_FOUND("404", "알림이 존재하지 않습니다."),
    DUPLICATION_NOTIFICATION("409", "이미 존재하는 알림입니다."),
    NOTIFICATION_DATE_OUT_OF_RANGE("400", "알림 날짜가 이벤트 기간에 포함되지 않습니다."),

    // OAuth
    INVALID_REFRESH_TOKEN("400", "유효하지 않은 Refresh Token입니다."),
    INVALID_TOKEN("400", "유효하지 않은 Access Token입니다."),
    INVALID_AUTHORIZATION_HEADER("400", "유효하지 않은 Authorization Header 입니다."),
    OAUTH_ERROR("500", "OAuth 처리 중 오류가 발생했습니다."),

    // Banner
    BANNER_NOT_FOUND("404", "배너를 찾을 수 없습니다."),

    // University
    UNIVERSITY_NOT_FOUND("404", "대학교를 찾을 수 없습니다."),

    //Restaurant
    RESTAURANT_NOT_FOUND("404", "맛집을 찾을 수 없습니다."),

    //Department
    DEPARTMENT_NOT_FOUND("404", "학과를 찾을 수 없습니다."),

    // Star
    STAR_NOT_FOUND("404", "즐겨찾기를 찾을 수 없습니다."),

    // Calender Event
    CALENDAR_EVENT_NOT_FOUND("404", "일정을 찾을 수 없습니다."),

    // Admission
    ADMISSION_NOT_FOUND("404", "입결 이미지 정보를 찾을 수 없습니다."),

    //TuitionFee
    TUITION_FEE_NOT_FOUND("404", "등록금 정보를 찾을 수 없습니다."),

    //News
    NEWS_NOT_FOUND("404", "기사 정보를 찾을 수 없습니다."),

    //Competition Rate
    COMPETITION_RATE_NOT_FOUND("404", "경쟁률 정보를 찾을 수 없습니다."),

    //Employment Rate
    EMPLOYMENT_RATE_NOT_FOUND("404", "취업률 정보를 찾을 수 없습니다."),

    //Admission Announcement
    EXPO_NOT_FOUND("404", "대학 연계 행사 정보를 찾을 수 없습니다."),
    EXPO_CATEGORY_NOT_FOUND("404", "대학 연계 행사 카테고리 정보를 찾을 수 없습니다."),

    //Matching
    MATCHING_RESULT_NOT_FOUND("404", "점수에 맞는 설문 결과를 찾을 수 없습니다."),

    //School
    SCHOOL_NOT_FOUND("404", "해당 학교를 찾을 수 없습니다."),
    SCHOOL_CHANGE_NOT_ALLOWED("403", "학교는 등록 후 1년이 지나야 변경할 수 있습니다."),

    //Board
    BOARD_NOT_FOUND("404", "해당 게시판을 찾을 수 없습니다."),
    BOARD_DUPULICATE("409", "이미 존재하는 게시판입니다."),

    //Post
    POST_NOT_FOUND("404", "해당 게시글을 찾을 수 없습니다."),
    POST_LIKE_NOT_FOUND("404", "해당 게시글 좋아요를 찾을 수 없습니다."),
    UNAUTHORIZED_ACCESS("403", "권한이 없습니다"),

    //Comment
    COMMENT_NOT_FOUND("404", "해당 댓글을 찾을 수 없습니다."),
    COMMENT_LIKE_NOT_FOUND("404", "해당 댓글 좋아요를 찾을 수 없습니다.");


    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}