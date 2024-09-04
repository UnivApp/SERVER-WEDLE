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

    // OAuth
    INVALID_REFRESH_TOKEN("400", "유효하지 않은 Refresh Token입니다."),
    OAUTH_ERROR("500", "OAuth 처리 중 오류가 발생했습니다."),

    // Banner
    BANNER_NOT_FOUND("404", "배너를 찾을 수 없습니다."),

    // University
    UNIVERSITY_NOT_FOUND("404", "대학교를 찾을 수 없습니다."),

    // Entrance Score
    ENTRANCE_SCORE_NOT_FOUND("404", "입결 이미지 정보를 찾을 수 없습니다.");

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