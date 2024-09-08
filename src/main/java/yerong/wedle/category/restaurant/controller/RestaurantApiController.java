package yerong.wedle.category.restaurant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.category.restaurant.dto.RestaurantResponse;
import yerong.wedle.category.restaurant.service.RestaurantService;

import java.util.List;

@Tag(name = "Restaurant API", description = "대학교 주변 맛집 정보 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/restaurants")
public class RestaurantApiController {

    private final RestaurantService restaurantService;

    @Operation(
            summary = "대학교 주변 식당 조회", description = "대학교 이름을 이용해 해당 대학교 주변의 식당 목록을 조회합니다."
    )
    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getRestaurantsByUniversityName(@RequestParam String universityName) {
        List<RestaurantResponse> restaurantResponses = restaurantService.getRestaurantsByUniversityName(universityName);
        return ResponseEntity.ok(restaurantResponses);
    }
}
