package yerong.wedle.report.domain;

public enum ReportType {
    AD("상업적 광고 및 판매"),
    INAPPROPRIATE_BOARD("게시판 유형에 부적절함"),
    CLICKBAIT("낚시"),
    SPAM("도배"),
    LEAK("유출"),
    IMPERSONATION("사칭"),
    FRAUD("사기"),
    ABUSE("욕설/비하"),
    OBSCENE("음란물/불건전한 내용");

    private String reason;

    private ReportType(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
