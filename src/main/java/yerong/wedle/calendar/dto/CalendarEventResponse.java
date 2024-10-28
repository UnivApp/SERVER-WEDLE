package yerong.wedle.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class CalendarEventResponse {

    private Long CalendarEventId;
    private String title;
    private LocalDate date;
    private String type;

    private boolean notificationActive;
    private Long notificationId;
}
