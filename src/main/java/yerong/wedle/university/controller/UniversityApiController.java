package yerong.wedle.university.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        List<UniversityResponse> universityResponses = universityService.searchUniversity(keyword);
        return ResponseEntity.ok(universityResponses);
    }

    @GetMapping("/{universityId}")
    public ResponseEntity<UniversityResponse> getUniversityById(@PathVariable Long id) {
        UniversityResponse university = universityService.getUniversityById(id);
        return ResponseEntity.ok(university);
    }

    @GetMapping
    public ResponseEntity<List<UniversityResponse>> getAllUniversities() {
        List<UniversityResponse> allUniversities = universityService.getAllUniversities();
        return ResponseEntity.ok(allUniversities);
    }
}
