package yerong.wedle.school.neis;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SchoolResult {
    @JsonProperty("CODE")
    private String code;
    @JsonProperty("MESSAGE")
    private String msg;
}
