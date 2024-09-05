package yerong.wedle.category.alumni.domain;

public enum AlumniCategory {
    PHYSICAL_EDUCATION("체육계"), // 체육계
    DIRECTOR("감독"), // 감독
    ACTOR("배우"), // 배우
    SINGER("가수"); // 가수

    private final String displayName;

    AlumniCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static AlumniCategory fromDisplayName(String displayName) {
        for (AlumniCategory category : AlumniCategory.values()) {
            if (category.getDisplayName().equals(displayName)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown display name: " + displayName);
    }
}