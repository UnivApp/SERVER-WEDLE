package yerong.wedle.category.expo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.category.expo.domain.ExpoStatus;
import yerong.wedle.category.expo.domain.ExpoType;
import yerong.wedle.category.expo.dto.ExpoResponse;
import yerong.wedle.category.expo.service.ExpoService;

import java.util.List;

@Tag(name = "Expo API", description = "대학교 입시설명회 및 연계 활동 공지 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/expo")
public class ExpoApiController {

    private final ExpoService expoService;


    @Operation(summary = "대학별 연계활동 조회", description = "특정 대학의 ID를 사용하여 해당 대학과 관련된 모든 공지사항을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "대학과 관련된 공지사항 조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 대학을 찾을 수 없음")
    })
    @GetMapping("/search")
    public ResponseEntity<List<ExpoResponse>> getExposByUniversity(@RequestParam String keyword) {
        return ResponseEntity.ok().body(expoService.getExposByKeyword(keyword));
    }

    @Operation(summary = "카테고리별 연계활동 조회", description = "특정 카테고리를 사용하여 해당 카테고리에 속한 모든 연계활동을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리별 연계활동 조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 카테고리에 연계활동이 없음")
    })
    @GetMapping("/type")
    public ResponseEntity<List<ExpoResponse>> getExposByType(@RequestParam ExpoType type) {
        return ResponseEntity.ok().body(expoService.getExposByType(type));
    }

    @Operation(summary = "상태별 연계활동 조회", description = "특정 카테고리를 사용하여 해당 카테고리에 속한 모든 연계활동을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리별 연계활동 조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 카테고리에 연계활동이 없음")
    })
    @GetMapping("/status")
    public ResponseEntity<List<ExpoResponse>> getExposByStatus(@RequestParam ExpoStatus status) {
        return ResponseEntity.ok().body(expoService.getExposByStatus(status));
    }

    @Operation(summary = "모든 연계활동 조회", description = "모든 연계활동을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모든 연계활동 조회 성공")
    })
    @GetMapping
    public ResponseEntity<List<ExpoResponse>> getAllExpos() {
        return ResponseEntity.ok().body(expoService.getAllExpos());
    }
}
