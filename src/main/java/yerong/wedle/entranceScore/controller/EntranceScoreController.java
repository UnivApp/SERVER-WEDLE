package yerong.wedle.entranceScore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.entranceScore.dto.EntranceScoreResponse;
import yerong.wedle.entranceScore.service.EntranceScoreService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/entrance-score-images")
public class EntranceScoreController {

    private final EntranceScoreService entranceScoreService;

    @GetMapping
    public ResponseEntity<EntranceScoreResponse> getImageByType(@RequestParam String type) {
        EntranceScoreResponse entranceScoreResponse = entranceScoreService.getImageByType(type);
        return ResponseEntity.ok(entranceScoreResponse);
    }
}
