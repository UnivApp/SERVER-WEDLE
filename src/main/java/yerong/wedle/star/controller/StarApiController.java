package yerong.wedle.star.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.oauth.princiapl.PrincipalDetails;
import yerong.wedle.star.service.StarService;

@Tag(name = "Star API", description = "대학교 별점 추가 및 제거 API")
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/stars")
public class StarApiController {

    private final StarService starService;

    @Operation(
            summary = "대학교 별점 추가",
            description = "대학교 이름을 기반으로 별점을 추가합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "별점 추가 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/add")
    public ResponseEntity<Void> addStar(@RequestParam String universityName) {
        try {
            starService.addStar(universityName);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("즐겨찾기 추가 실패", e);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<Void> removeStar(@RequestParam String universityName) {
        try {
            starService.removeStar(universityName);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("즐겨찾기 제거 실패", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
