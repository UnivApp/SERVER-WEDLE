package yerong.wedle.category.restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.category.restaurant.dto.RestaurantResponse;
import yerong.wedle.category.restaurant.service.RestaurantService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/restaurants")
public class RestaurantApiController {

    private final RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getRestaurantsByUniversityName(@RequestParam String universityName) {
        List<RestaurantResponse> restaurantResponses = restaurantService.getRestaurantsByUniversityName(universityName);
        return ResponseEntity.ok(restaurantResponses);
    }
}
