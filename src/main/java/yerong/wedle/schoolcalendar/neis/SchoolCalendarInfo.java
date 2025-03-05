package yerong.wedle.schoolcalendar.neis;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class SchoolCalendarInfo {
    @JsonProperty("head")
    private List<SchoolCalendarHead> head;

    @JsonProperty("row")
    private List<NeisSchoolCalendarResponse> row;
}
