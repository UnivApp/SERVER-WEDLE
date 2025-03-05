package yerong.wedle.schoolcalendar.neis;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class SchoolCalendarResult {
    @JsonProperty("CODE")
    private String code;
    @JsonProperty("MESSAGE")
    private String msg;
}
