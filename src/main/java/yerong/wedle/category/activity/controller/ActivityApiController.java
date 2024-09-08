package yerong.wedle.category.activity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.category.activity.dto.ActivityResponse;
import yerong.wedle.category.activity.service.ActivityService;

import java.util.List;

@Tag(name = "Activity API", description = "대학교 활동 정보 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/activities")
public class ActivityApiController {
    private final ActivityService activityService;

    @Operation(
            summary = "대학교 활동 조회", description = "대학교 이름을 이용해 해당 대학교의 활동 목록을 조회합니다."
    )
    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getActivitiesByUniversityName(@RequestParam String universityName) {
        List<ActivityResponse> activities = activityService.getActivitiesByUniversityName(universityName);
        return ResponseEntity.ok(activities);
    }
}
