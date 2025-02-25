package yerong.wedle.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.school.dto.SchoolRegistrationRequest;
import yerong.wedle.school.neis.NeisSchoolResponse;
import yerong.wedle.school.service.SchoolService;

@Tag(name = "School API", description = "학교 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/school")
public class SchoolApiController {
    private final SchoolService schoolService;

    @Operation(summary = "학교 리스트 검색", description = "회원이 선택할 수 있는 학교 목록을 반환합니다.")
    @GetMapping("/list")
    public ResponseEntity<List<NeisSchoolResponse>> getSchoolList(
            @Parameter(description = "검색할 키워드 (학교명)", required = true) @RequestParam String keyword) {
        List<NeisSchoolResponse> neisSchoolResponses = schoolService.searchSchool(keyword);
        return ResponseEntity.ok(neisSchoolResponses);
    }

    @Operation(summary = "학교 선택 및 등록", description = "사용자가 선택한 학교를 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "학교 변경 성공"),
            @ApiResponse(responseCode = "403", description = "학교 변경 불가(1년이 지나지 않은 상태)"),
            @ApiResponse(responseCode = "404", description = "학교를 찾을 수 없음")})
    @PostMapping("/register")
    public ResponseEntity<Void> registerSchool(SchoolRegistrationRequest schoolRegistrationRequest) {
        schoolService.setSchool(schoolRegistrationRequest);
        return ResponseEntity.ok().build();
    }
}
