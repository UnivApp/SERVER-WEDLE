package yerong.wedle.category.activity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.category.activity.dto.ActivityResponse;
import yerong.wedle.category.activity.service.ActivityService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/activities")
public class ActivityApiController {
    private final ActivityService activityService;

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getActivitiesByUniversityName(@RequestParam String universityName) {
        List<ActivityResponse> activities = activityService.getActivitiesByUniversityName(universityName);
        return ResponseEntity.ok(activities);
    }
}
