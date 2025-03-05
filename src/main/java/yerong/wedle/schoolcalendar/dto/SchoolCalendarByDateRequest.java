package yerong.wedle.schoolcalendar.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class SchoolCalendarByDateRequest {
    private LocalDate date;
}
