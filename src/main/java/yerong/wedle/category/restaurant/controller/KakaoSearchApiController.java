package yerong.wedle.category.restaurant.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.category.restaurant.dto.RestaurantResponse;
import yerong.wedle.category.restaurant.service.KakaoSearchApiService;

@RequiredArgsConstructor
@RequestMapping("/api/kakao-search")
@RestController
public class KakaoSearchApiController {

    private final KakaoSearchApiService kakaoSearchApiService;

    @GetMapping("/kakao")
    public ResponseEntity<List<RestaurantResponse>> kakaoSearchDynamic(@RequestParam String universityName) {
        List<RestaurantResponse> restaurantResponses = kakaoSearchApiService.searchRestaurant(universityName + " 맛집");
        return ResponseEntity.ok(restaurantResponses);
    }
}
