package yerong.wedle.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.school.dto.SchoolResponse;
import yerong.wedle.school.service.SchoolService;

@Tag(name = "School API", description = "학교 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/school")
public class SchoolApiController {
    private final SchoolService schoolService;

    @Operation(
            summary = "학교 리스트 검색",
            description = "회원이 선택할 수 있는 학교 목록을 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "학교 목록 반환"),
            }
    )
    @GetMapping("/list")
    public ResponseEntity<List<SchoolResponse>> getSchoolList(
            @Parameter(description = "검색할 키워드 (학교명)", required = true) @RequestParam String keyword) {
        List<SchoolResponse> schoolResponses = schoolService.searchSchool(keyword);
        return ResponseEntity.ok(schoolResponses);
    }
}
