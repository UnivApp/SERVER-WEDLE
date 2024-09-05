package yerong.wedle.category.ranking.domain;

public enum RankingType {
    QS("QS 세계대학평가"),
    THE("THE 세계대학평가"),
    ARWU("ARWU 세계대학평가"),
    CWUR("CWUR 세계대학평가"),
    USN_WR("US News & World Report"),
    CWTS("CWTS 세계대학평가"),
    NATURE_INDEX("Nature Index");

    private final String displayName;

    RankingType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static RankingType fromDisplayName(String displayName) {
        for (RankingType type : values()) {
            if (type.getDisplayName().equalsIgnoreCase(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown display name: " + displayName);
    }
}