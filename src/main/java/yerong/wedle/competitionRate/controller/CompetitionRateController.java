package yerong.wedle.competitionRate.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.competitionRate.dto.UniversityCompetitionRateResponse;
import yerong.wedle.competitionRate.service.CompetitionRateService;

import java.util.List;

@Tag(name = "CompetitionRate API", description = "대학교 경쟁률 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/competition-rate")
public class CompetitionRateController {
    private final CompetitionRateService competitionRateService;
    @Operation(
            summary = "대학교 경쟁률 조회",
            description = "대학교 ID를 이용해 해당 대학교의 경쟁률 삼년치를 조회합니다."
    )
    @GetMapping()
    public ResponseEntity<UniversityCompetitionRateResponse> getAllCompetitionRates(@RequestParam Long universityId) {
        UniversityCompetitionRateResponse universityCompetitionRates = competitionRateService.getUniversityCompetitionRates(universityId);
        return ResponseEntity.ok(universityCompetitionRates);
    }

    @Operation(
            summary = "가나다순으로 상위 5개 대학교 경쟁률 조회",
            description = "가나다순으로 상위 5개 대학교의 경쟁률을 조회합니다."
    )
    @GetMapping("/top-5")
    public ResponseEntity<List<UniversityCompetitionRateResponse>> getTop5UniversitiesCompetitionRates() {
        List<UniversityCompetitionRateResponse> top5Rates = competitionRateService.getTop5UniversitiesCompetitionRates();
        return ResponseEntity.ok(top5Rates);
    }
}
