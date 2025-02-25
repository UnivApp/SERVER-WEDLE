package yerong.wedle.school.neis;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SchoolHead {
    @JsonProperty("list_total_count")
    private int listTotalCount;
    @JsonProperty("RESULT")
    private SchoolResult schoolResult;
}
