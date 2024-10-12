package yerong.wedle.university.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yerong.wedle.university.dto.UniversityAllResponse;
import yerong.wedle.university.dto.UniversityResponse;
import yerong.wedle.university.service.UniversityService;

import java.util.List;

@Tag(name = "University API", description = "대학교 정보 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/universities")
public class UniversityApiController {

    private final UniversityService universityService;

    @Operation(summary = "대학교 검색", description = "키워드를 이용해 대학교를 검색합니다. 키워드는 학교명 또는 소재지를 의미합니다.")
    @GetMapping("/search")
    public ResponseEntity<List<UniversityResponse>> searchUniversities(
            @Parameter(description = "검색할 키워드 (학교명 또는 소재지)", required = true) @RequestParam String keyword) {
        List<UniversityResponse> universityResponses = universityService.searchUniversitiesSummary(keyword);
        return ResponseEntity.ok(universityResponses);
    }

    @Operation(summary = "대학교 요약 정보 조회", description = "대학교 ID를 이용해 로고와 학교 이름을 포함한 요약 정보를 조회합니다.")
    @GetMapping("/summary/{universityId}")
    public ResponseEntity<UniversityResponse> getUniversityById(
            @Parameter(description = "대학교 ID", required = true) @PathVariable("universityId") Long id) {
        UniversityResponse university = universityService.getUniversitySummaryById(id);
        return ResponseEntity.ok(university);
    }

    @Operation(summary = "모든 대학교 요약 정보 조회", description = "모든 대학교의 로고와 학교 이름을 포함한 요약 정보를 조회합니다.")
    @GetMapping("/summary")
    public ResponseEntity<List<UniversityResponse>> getAllUniversitiesSummary() {
        List<UniversityResponse> allUniversities = universityService.getAllUniversitiesSummary();
        return ResponseEntity.ok(allUniversities);
    }

    @Operation(summary = "대학교 상세 정보 조회", description = "대학교 ID를 이용해 모든 정보를 조회합니다.")
    @GetMapping("/details/{universityId}")
    public ResponseEntity<UniversityAllResponse> getUniversityDetailsById(
            @Parameter(description = "대학교 ID", required = true) @PathVariable("universityId") Long id) {
        UniversityAllResponse university = universityService.getUniversityDetailsById(id);
        return ResponseEntity.ok(university);
    }

    @Operation(summary = "모든 대학교 상세 정보 조회", description = "모든 대학교에 대한 모든 정보를 조회합니다.")
    @GetMapping("/details")
    public ResponseEntity<List<UniversityAllResponse>> getAllUniversitiesDetails() {
        List<UniversityAllResponse> allUniversities = universityService.getAllUniversitiesDetails();
        return ResponseEntity.ok(allUniversities);
    }

}
