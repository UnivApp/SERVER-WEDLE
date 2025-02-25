package yerong.wedle.neis;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
    @JsonProperty("CODE")
    private String code;
    @JsonProperty("MESSAGE")
    private String msg;
}
