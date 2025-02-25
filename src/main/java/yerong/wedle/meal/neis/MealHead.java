package yerong.wedle.meal.neis;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MealHead {
    @JsonProperty("list_total_count")
    private int listTotalCount;
    @JsonProperty("RESULT")
    private MealResult result;
}
