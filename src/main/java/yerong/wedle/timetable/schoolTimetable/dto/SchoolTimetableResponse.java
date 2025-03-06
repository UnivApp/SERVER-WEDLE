package yerong.wedle.timetable.schoolTimetable.dto;

import java.util.List;
import lombok.Data;

@Data
public class SchoolTimetableResponse {
    private Long schoolTimetableId;
    private String title;
    private List<SchoolScheduleResponse> schedules;

    public SchoolTimetableResponse(Long schoolTimetableId, String title, List<SchoolScheduleResponse> schedules) {
        this.schoolTimetableId = schoolTimetableId;
        this.title = title;
        this.schedules = schedules;
    }
}
