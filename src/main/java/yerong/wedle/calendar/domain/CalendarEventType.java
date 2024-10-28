package yerong.wedle.calendar.domain;

public enum CalendarEventType {

    COLLEGE_ENTRANCE_EXAM("수능"),
    UNIVERSITY_COOPERATION_EVENT("대학 연계 행사"),
    EARLY_ADMISSION("수시 접수"),
    REGULAR_ADMISSION("정시 접수");

    private final String displayName;

    CalendarEventType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
