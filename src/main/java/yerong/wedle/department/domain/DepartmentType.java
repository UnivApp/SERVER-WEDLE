package yerong.wedle.department.domain;

public enum DepartmentType {
    NATURAL_SCIENCES("자연계열"),
    HUMANITIES("인문계열"),
    ARTS("예체능계열"),
    ENGINEERING("공학계열");

    private final String displayName;

    DepartmentType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
