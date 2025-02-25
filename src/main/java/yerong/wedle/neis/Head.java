package yerong.wedle.neis;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Head {
    @JsonProperty("list_total_count")
    private int listTotalCount;
    @JsonProperty("RESULT")
    private Result result;
}
