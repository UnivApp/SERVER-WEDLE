package yerong.wedle.category.ranking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.category.ranking.dto.RankingResponse;
import yerong.wedle.category.ranking.service.RankingService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rankings")
public class RankingApiController {

    private final RankingService rankingService;

    @GetMapping
    public ResponseEntity<List<RankingResponse>> getRankingsByUniversityName(@RequestParam String universityName) {
        List<RankingResponse> rankings = rankingService.getRankingsByUniversityName(universityName);
        return ResponseEntity.ok(rankings);
    }
}
