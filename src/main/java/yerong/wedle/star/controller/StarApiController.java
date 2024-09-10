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
import org.springframework.web.bind.annotation.*;
import yerong.wedle.oauth.princiapl.PrincipalDetails;
import yerong.wedle.star.service.StarService;
import yerong.wedle.university.dto.UniversityResponse;

import java.util.List;

@Tag(name = "Star API", description = "대학교 별점 추가 및 제거 API")
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/stars")
public class StarApiController {

    private final StarService starService;

    @Operation(
            summary = "대학교 즐겨찾기 추가",
            description = "대학교 ID를 기반으로 즐겨찾기를 추가합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "즐겨찾기 추가 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/add")
    public ResponseEntity<Void> addStar(@RequestParam Long universityId) {
        try {
            starService.addStar(universityId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("즐겨찾기 추가 실패", e);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @Operation(
            summary = "대학교 즐겨찾기 제거",
            description = "대학교 ID를 기반으로 즐겨찾기를 제거합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "즐겨찾기 제거 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/remove")
    public ResponseEntity<Void> removeStar(@RequestParam Long universityId) {
        try {
            starService.removeStar(universityId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("즐겨찾기 제거 실패", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "전체 조회 성공"),
            @ApiResponse(responseCode = "404", description = "즐겨찾기를 추가한 대학교가 없음")
    })
    @GetMapping("/list")
    public ResponseEntity<List<UniversityResponse>> getStarredUniversities() {
        try {
            List<UniversityResponse> starredUniversities = starService.getStarredUniversities();
            if (starredUniversities.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(starredUniversities);
        } catch (Exception e) {
            log.error("즐겨찾기가 추가된 대학교 조회 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
