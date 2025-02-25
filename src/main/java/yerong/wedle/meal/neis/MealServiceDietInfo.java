package yerong.wedle.meal.neis;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class MealServiceDietInfo {
    @JsonProperty("head")
    private List<MealHead> mealHead;

    @JsonProperty("row")
    private List<NeisMealResponse> row;
}
