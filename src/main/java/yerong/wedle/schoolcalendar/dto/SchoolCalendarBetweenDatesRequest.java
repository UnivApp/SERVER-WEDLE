package yerong.wedle.schoolcalendar.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class SchoolCalendarBetweenDatesRequest {
    private LocalDate startDate;
    private LocalDate endDate;
}
