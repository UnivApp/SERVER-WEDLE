package yerong.wedle.employmentRate.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.competitionRate.dto.CompetitionRateResponse;
import yerong.wedle.employmentRate.dto.EmploymentRateResponse;
import yerong.wedle.employmentRate.dto.UniversityEmploymentRateResponse;
import yerong.wedle.employmentRate.service.EmploymentRateService;

import java.util.List;

@Tag(name = "EmploymentRate API", description = "대학교 취업률 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employment-rate")
public class EmploymentRateController {

        private final EmploymentRateService employmentRateService;
    @Operation(
            summary = "대학교 취업률 삼년치 조회",
            description = "대학교 ID를 이용해 해당 대학교의 취업률 삼년치를 조회합니다."
    )
    @GetMapping()
    public ResponseEntity<UniversityEmploymentRateResponse> getAllEmploymentRates(@RequestParam Long universityId) {
        UniversityEmploymentRateResponse universityEmploymentRates = employmentRateService.getUniversityEmploymentRates(universityId);
        return ResponseEntity.ok(universityEmploymentRates);
    }

    @Operation(
            summary = "가나다순으로 상위 5개 대학교 취업률 조회",
            description = "가나다순으로 상위 5개 대학교의 취업률을 조회합니다."
    )
    @GetMapping("/top-5")
    public ResponseEntity<List<UniversityEmploymentRateResponse>> getTop5UniversitiesEmploymentRates () {
        List<UniversityEmploymentRateResponse> top5UniversitiesEmploymentRates = employmentRateService.getTop5UniversitiesEmploymentRates();
        return ResponseEntity.ok(top5UniversitiesEmploymentRates);
    }
}
