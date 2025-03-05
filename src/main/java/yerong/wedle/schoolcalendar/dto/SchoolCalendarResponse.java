package yerong.wedle.schoolcalendar.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class SchoolCalendarResponse {
    private LocalDate date;
    private List<EventDetailsResponse> events;

    public SchoolCalendarResponse(LocalDate date, List<EventDetailsResponse> events) {
        this.date = date;
        this.events = events;
    }
}
