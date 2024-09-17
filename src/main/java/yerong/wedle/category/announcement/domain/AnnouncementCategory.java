package yerong.wedle.category.announcement.domain;

public enum AnnouncementCategory {
    ADMISSION_SESSION("입시설명회"),
    COOPERATIVE_ACTIVITIES("연계활동");

    private final String displayName;

    AnnouncementCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}