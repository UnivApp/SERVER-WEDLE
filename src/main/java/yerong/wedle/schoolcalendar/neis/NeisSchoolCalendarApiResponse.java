package yerong.wedle.schoolcalendar.neis;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;
import lombok.Getter;

@Getter
public class NeisSchoolCalendarApiResponse {

    @JsonProperty("SchoolSchedule")
    private List<SchoolCalendarInfo> schoolSchedule;

    public List<NeisSchoolCalendarResponse> getSchoolSchedule() {
        if (schoolSchedule != null && !schoolSchedule.isEmpty()) {
            for (SchoolCalendarInfo info : schoolSchedule) {
                if (info.getHead() != null && !info.getHead().isEmpty()) {
                    SchoolCalendarResult result = info.getHead().get(0).getSchoolResult();
                    if (result != null && "INFO-200".equals(result.getCode())) {
                        return Collections.emptyList();
                    }
                }
                if (info.getRow() != null) {
                    return info.getRow();
                }
            }
        }
        return Collections.emptyList();
    }
}
