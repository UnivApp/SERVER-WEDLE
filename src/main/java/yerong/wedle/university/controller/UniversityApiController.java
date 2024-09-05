package yerong.wedle.university.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yerong.wedle.university.dto.UniversityAllResponse;
import yerong.wedle.university.dto.UniversityResponse;
import yerong.wedle.university.service.UniversityService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/universities")
public class UniversityApiController {

    private final UniversityService universityService;

    @GetMapping("/search")
    public ResponseEntity<List<UniversityResponse>> searchUniversities(@RequestParam String keyword) {
        List<UniversityResponse> universityResponses = universityService.searchUniversitiesSummary(keyword);
        return ResponseEntity.ok(universityResponses);
    }

    @GetMapping("/summary/{universityId}")
    public ResponseEntity<UniversityResponse> getUniversityById(@PathVariable Long id) {
        UniversityResponse university = universityService.getUniversitySummaryById(id);
        return ResponseEntity.ok(university);
    }

    @GetMapping("/summary")
    public ResponseEntity<List<UniversityResponse>> getAllUniversitiesSummary() {
        List<UniversityResponse> allUniversities = universityService.getAllUniversitiesSummary();
        return ResponseEntity.ok(allUniversities);
    }
    @GetMapping("/details/{universityId}")
    public ResponseEntity<UniversityAllResponse> getUniversityDetailsById(@PathVariable("universityId") Long id) {
        UniversityAllResponse university = universityService.getUniversityDetailsById(id);
        return ResponseEntity.ok(university);
    }

    @GetMapping("/details")
    public ResponseEntity<List<UniversityAllResponse>> getAllUniversitiesDetails() {
        List<UniversityAllResponse> allUniversities = universityService.getAllUniversitiesDetails();
        return ResponseEntity.ok(allUniversities);
    }
}
