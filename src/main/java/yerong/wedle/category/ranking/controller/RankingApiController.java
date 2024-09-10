package yerong.wedle.category.ranking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.category.ranking.dto.RankingResponse;
import yerong.wedle.category.ranking.service.RankingService;

import java.util.List;

@Tag(name = "Ranking API", description = "대학교 랭킹 정보 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rankings")
public class RankingApiController {

    private final RankingService rankingService;


    @Operation(
            summary = "대학교 랭킹 정보 조회", description = "대학교 ID를 이용해 해당 대학교의 랭킹 목록을 조회합니다."
    )
    @GetMapping
    public ResponseEntity<List<RankingResponse>> getRankingsByUniversityId(@RequestParam Long universityId) {
        List<RankingResponse> rankings = rankingService.getRankingsByUniversityId(universityId);
        return ResponseEntity.ok(rankings);
    }
}
