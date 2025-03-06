package yerong.wedle.timetable.personalTimetable.dto;

import java.util.List;
import lombok.Data;

@Data
public class PersonalTimetableResponse {
    private Long personalTimetableId;
    private List<PersonalScheduleResponse> schedules;

    public PersonalTimetableResponse(Long schoolTimetableId, List<PersonalScheduleResponse> schedules) {
        this.personalTimetableId = schoolTimetableId;
        this.schedules = schedules;
    }
}
