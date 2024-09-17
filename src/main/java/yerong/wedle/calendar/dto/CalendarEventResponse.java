package yerong.wedle.calendar.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class CalendarEventResponse {

    private Long id;
    private LocalDate date;
    private String content;

    public CalendarEventResponse(Long id, LocalDate date, String content) {
        this.id = id;
        this.date = date;
        this.content = content;
    }
}
