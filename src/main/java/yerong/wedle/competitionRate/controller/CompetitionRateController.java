package yerong.wedle.competitionRate.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.category.activity.dto.ActivityResponse;
import yerong.wedle.competitionRate.dto.CompetitionRateResponse;
import yerong.wedle.competitionRate.service.CompetitionRateService;

import java.util.List;

@Tag(name = "CompetitionRate API", description = "대학교 경쟁률 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/competition-rate")
public class CompetitionRateController {
    private final CompetitionRateService competitionRateService;
    @Operation(
            summary = "대학교 경쟁률 삼년치 조회",
            description = "대학교 ID를 이용해 해당 대학교의 경쟁률 삼년치를 조회합니다."
    )
    @GetMapping("/three-years")
    public ResponseEntity<List<CompetitionRateResponse>> getAllCompetitionRates(@RequestParam Long universityId) {
        List<CompetitionRateResponse> lastThreeYearsCompetitionRates = competitionRateService.getLastThreeYearsCompetitionRates(universityId);
        return ResponseEntity.ok(lastThreeYearsCompetitionRates);
    }

    @Operation(
            summary = "대학교 경쟁률 최근년도 조회",
            description = "대학교 ID를 이용해 해당 대학교의 경쟁률 최근년도를 조회합니다."
    )
    @GetMapping
    public ResponseEntity<CompetitionRateResponse> getCompetitionRate(@RequestParam Long universityId) {
        CompetitionRateResponse latestCompetitionRate = competitionRateService.getLatestCompetitionRate(universityId);
        return ResponseEntity.ok(latestCompetitionRate);
    }
}
