package yerong.wedle.timetable.personalTimetable.dto;

import java.time.LocalTime;
import lombok.Data;

@Data
public class PersonalScheduleResponse {
    private Long personalScheduleId;
    private String day;
    private LocalTime startTime;
    private LocalTime endTime;
    private String title;
    private String color;

    public PersonalScheduleResponse(Long personalScheduleId, String day, LocalTime startTime, LocalTime endTime,
                                    String title,
                                    String color) {
        this.personalScheduleId = personalScheduleId;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.color = color;
    }
}
