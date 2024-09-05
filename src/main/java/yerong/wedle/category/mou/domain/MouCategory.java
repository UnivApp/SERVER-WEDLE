package yerong.wedle.category.mou.domain;

public enum MouCategory {
    LARGE_ENTERPRISE("대기업"),
    MEDIUM_ENTERPRISE("중견기업"),
    PUBLIC_INSTITUTION("공공기관"),
    MILITARY("국군"),
    INTER_UNIVERSITY("대학 간 협력"),
    INTERNATIONAL_COOPERATION("국제 협력");

    private final String displayName;

    MouCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static MouCategory fromDisplayName(String displayName) {
        for (MouCategory category : MouCategory.values()) {
            if (category.getDisplayName().equals(displayName)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown display name: " + displayName);
    }
}
