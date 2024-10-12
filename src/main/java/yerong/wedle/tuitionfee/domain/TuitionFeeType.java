package yerong.wedle.tuitionfee.domain;

public enum TuitionFeeType {
    HUMANITIES("인문사회계열"),
    NATURAL_SCIENCES("자연과학계열"),
    ENGINEERING("공학계열"),
    ARTS("예체능계열"),
    MEDICAL_SCIENCES("의학계열"),
    AVERAGE("학교평균");



    private final String displayName;

    TuitionFeeType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
