package yerong.wedle.meal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.meal.dto.MealDateResponse;
import yerong.wedle.meal.dto.MealRequest;
import yerong.wedle.meal.dto.MealResponse;
import yerong.wedle.meal.service.MealService;

@Tag(name = "Meal API", description = "급식 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/meal")
public class MealApiController {
    private final MealService mealService;


    @Operation(summary = "급식 날짜 지정 검색", description = "해당 학교의 해당 날짜 급식 메뉴를 반환합니다.")
    @GetMapping("/list/date")
    public ResponseEntity<List<MealResponse>> getMealsByDate(MealRequest mealRequest) {
        List<MealResponse> mealResponses = mealService.getMealsByDate(mealRequest);
        return ResponseEntity.ok(mealResponses);
    }

    @Operation(summary = "향후 2주치 급식 날짜 반환", description = "오늘부터 2주치의 급식 날짜를 반환합니다.")
    @GetMapping("/upcoming-dates")
    public ResponseEntity<List<MealDateResponse>> getUpcomingMealDates() {
        List<MealDateResponse> upcomingDates = mealService.getUpcomingMealDates();
        return ResponseEntity.ok(upcomingDates);
    }
}
