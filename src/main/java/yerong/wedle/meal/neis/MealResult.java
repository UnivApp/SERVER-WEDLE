package yerong.wedle.meal.neis;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MealResult {
    @JsonProperty("CODE")
    private String code;
    @JsonProperty("MESSAGE")
    private String msg;
}
