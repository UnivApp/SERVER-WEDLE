package yerong.wedle.timetable.schoolTimetable.dto;

import lombok.Data;

@Data
public class SchoolScheduleResponse {
    private Long id;
    private String day;
    private int period;
    private String subject;
    private String teacher;
    private String color;

    public SchoolScheduleResponse(Long id, String day, int period, String subject, String teacher, String color) {
        this.id = id;
        this.day = day;
        this.period = period;
        this.subject = subject;
        this.teacher = teacher;
        this.color = color;
    }
}
