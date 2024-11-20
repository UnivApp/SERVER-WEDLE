package yerong.wedle.category.event.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.category.event.dto.UniversityFestivalResponse;
import yerong.wedle.category.event.service.FestivalService;

@Tag(name = "Festival API", description = "대학교 관련 축제 정보 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/festivals")
public class FestivalApiController {
    private final FestivalService eventService;

    @Operation(
            summary = "대학교 이벤트 조회",
            description = "대학교 ID를 이용해 해당 대학교에서 진행된 이벤트 목록을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공적으로 축제 목록 조회"),
                    @ApiResponse(responseCode = "404", description = "해당 대학교를 찾을 수 없음")
            }
    )
    @GetMapping
    public ResponseEntity<UniversityFestivalResponse> getEventsByUniversityId(@RequestParam Long universityId) {
        UniversityFestivalResponse eventsByUniversityId = eventService.getFestivalsByUniversityId(universityId);
        return ResponseEntity.ok(eventsByUniversityId);
    }
}