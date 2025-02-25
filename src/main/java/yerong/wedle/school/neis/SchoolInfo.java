package yerong.wedle.school.neis;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class SchoolInfo {
    @JsonProperty("head")
    private List<Head> head;

    @JsonProperty("row")
    private List<NeisSchoolResponse> row;
}
