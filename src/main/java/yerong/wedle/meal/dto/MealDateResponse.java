package yerong.wedle.meal.dto;

import lombok.Data;

@Data
public class MealDateResponse {
    private String date;
    private String formattedDate;

    public MealDateResponse(String date, String formattedDate) {
        this.date = date;
        this.formattedDate = formattedDate;
    }
}
