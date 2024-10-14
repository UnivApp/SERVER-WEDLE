package yerong.wedle.category.expo.domain;

import lombok.Getter;

@Getter
public enum ExpoStatus {
    OPEN("접수 중"),
    CLOSED("접수 종료");

    private final String displayName;

    ExpoStatus(String displayName) {
        this.displayName = displayName;
    }
}
