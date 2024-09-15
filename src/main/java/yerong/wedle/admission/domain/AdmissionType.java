package yerong.wedle.admission.domain;

public enum AdmissionType {
    REGULAR("정시"),
    EARLY("수시");

    private final String displayName;

    AdmissionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}