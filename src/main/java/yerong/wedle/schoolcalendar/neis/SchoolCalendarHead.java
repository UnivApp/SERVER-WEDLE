package yerong.wedle.schoolcalendar.neis;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class SchoolCalendarHead {
    @JsonProperty("list_total_count")
    private int listTotalCount;
    @JsonProperty("RESULT")
    private SchoolCalendarResult schoolResult;
}
