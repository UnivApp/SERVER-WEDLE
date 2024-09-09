package yerong.wedle.entranceScore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.entranceScore.dto.EntranceScoreResponse;
import yerong.wedle.entranceScore.service.EntranceScoreService;

@Tag(name = "Entrance Score API", description = "입결 이미지 정보 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/entrance-score-images")
public class EntranceScoreController {

    private final EntranceScoreService entranceScoreService;

    @Operation(
            summary = "입학 성적 이미지 조회", description = "입학 성적 이미지의 유형을 기반으로 해당 이미지를 조회합니다."
    )
    @GetMapping
    public ResponseEntity<EntranceScoreResponse> getImage() {
        EntranceScoreResponse entranceScoreResponse = entranceScoreService.getImage();
        return ResponseEntity.ok(entranceScoreResponse);
    }
}
