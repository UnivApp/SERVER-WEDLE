package yerong.wedle.meal.neis;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class NeisMealApiResponse {

    @JsonProperty("mealServiceDietInfo")
    private List<MealServiceDietInfo> mealServiceDietInfo;

    public List<NeisMealResponse> getSchoolList() {
        if (mealServiceDietInfo != null && !mealServiceDietInfo.isEmpty()) {
            for (MealServiceDietInfo info : mealServiceDietInfo) {
                if (info.getRow() != null) {
                    return info.getRow();
                }
            }
        }
        return null;
    }
}
