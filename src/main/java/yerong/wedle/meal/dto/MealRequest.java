package yerong.wedle.meal.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class MealRequest {
    private LocalDate date;
}
