package yerong.wedle.category.restaurant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.category.restaurant.dto.RestaurantResponse;
import yerong.wedle.category.restaurant.service.KakaoSearchApiService;

@Tag(name = "Restaurant API", description = "맛집 검색 관련 API")
@RequiredArgsConstructor
@RequestMapping("/api/kakao-search")
@RestController
public class RestaurantApiController {

    private final KakaoSearchApiService kakaoSearchApiService;


    @Operation(summary = "Kakao 맛집 검색", description = "주어진 대학 이름을 기준으로 Kakao API를 통해 맛집 정보를 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "맛집 검색 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/kakao")
    public ResponseEntity<List<RestaurantResponse>> kakaoSearchDynamic(@RequestParam String universityName) {
        List<RestaurantResponse> restaurantResponses = kakaoSearchApiService.searchRestaurant(universityName);
        return ResponseEntity.ok(restaurantResponses);
    }
}
