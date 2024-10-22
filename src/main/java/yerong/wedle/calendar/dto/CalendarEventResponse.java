package yerong.wedle.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class CalendarEventResponse {

    private Long id;
    private String title;
    private LocalDate date;
    private String type;
    private boolean isNotified;

}
