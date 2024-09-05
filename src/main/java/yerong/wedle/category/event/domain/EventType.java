package yerong.wedle.category.event.domain;

import lombok.Getter;

@Getter
public enum EventType {
    FESTIVAL("축제"),
    SNACK_NIGHT_EVENT("간식/야식행사"),
    MT("MT"),
    SPORTS_DAY("운동회"),
    OTHER("기타");

    private final String displayName;

    EventType(String displayName) {
        this.displayName = displayName;
    }
}
