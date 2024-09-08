package yerong.wedle.category.rent.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.category.rent.dto.RentResponse;
import yerong.wedle.category.rent.service.RentService;

import java.util.List;

@Tag(name = "Rent API", description = "대학교 주변 임대 정보 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rents")
public class RentApiController {

    private final RentService rentService;

    @Operation(
            summary = "대학교 주변 임대 정보 조회", description = "대학교 이름을 이용해 해당 대학교 주변의 임대 목록을 조회합니다."
    )
    @GetMapping
    public ResponseEntity<List<RentResponse>> getRentsByUniversityName(@RequestParam String universityName) {
        List<RentResponse> rents = rentService.getRentsByUniversityName(universityName);
        return ResponseEntity.ok(rents);
    }
}
