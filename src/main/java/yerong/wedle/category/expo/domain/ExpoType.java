package yerong.wedle.category.expo.domain;

public enum ExpoType {
    ADMISSION_SESSION("입시설명회"),
    COOPERATIVE_ACTIVITIES("연계활동"),
    MAJOR_EXPERIENCE("전공체험"),
    OTHER("기타");


    private final String displayName;

    ExpoType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}