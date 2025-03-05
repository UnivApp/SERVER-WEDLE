package yerong.wedle.schoolcalendar.dto;

import java.util.List;
import lombok.Data;

@Data
public class TotalSchoolCalendarResponse {
    private String schoolName;
    private boolean isEmpty;
    private List<SchoolCalendarResponse> schoolCalendarResponses;

    public TotalSchoolCalendarResponse(String schoolName, List<SchoolCalendarResponse> schoolCalendarResponses) {
        this.schoolName = schoolName;
        this.isEmpty = schoolCalendarResponses == null || schoolCalendarResponses.isEmpty();
        this.schoolCalendarResponses = schoolCalendarResponses;
    }
}
