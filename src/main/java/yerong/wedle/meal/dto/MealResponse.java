package yerong.wedle.meal.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MealResponse {
    private String schoolName;
    private LocalDate date;
    private String calories;
    private String mealType;
    private List<String> dishNames;
}
