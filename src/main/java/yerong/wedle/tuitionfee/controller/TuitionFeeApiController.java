package yerong.wedle.tuitionfee.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.department.domain.DepartmentType;
import yerong.wedle.department.dto.DepartmentResponse;
import yerong.wedle.department.service.DepartmentService;
import yerong.wedle.tuitionfee.dto.TuitionFeeResponse;
import yerong.wedle.tuitionfee.service.TuitionFeeService;

import java.util.List;

@Tag(name = "TuitionFee API", description = "대학교 등록금 정보 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tuition-fees")
public class TuitionFeeApiController {

    private final TuitionFeeService tuitionFeeService;
    @Operation(summary = "계열별 등록금 조회", description = "대학 ID에 따라 각 계열별 등록금을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계열별 등록금 조회 성공"),
            @ApiResponse(responseCode = "404", description = "대학을 찾을 수 없음")
    })
    @GetMapping("/university/{universityId}/by-type")
    public ResponseEntity<List<TuitionFeeResponse>> getTuitionFeesByType(@PathVariable Long universityId) {
        return ResponseEntity.ok().body(tuitionFeeService.getTuitionFeesByType(universityId));
    }

    @Operation(summary = "전체 평균 등록금 조회", description = "대학 ID에 따라 전체 평균 등록금을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전체 평균 등록금 조회 성공"),
            @ApiResponse(responseCode = "404", description = "대학을 찾을 수 없음")
    })
    @GetMapping("/university/{universityId}/average")
    public ResponseEntity<TuitionFeeResponse> getAverageTuitionFee(@PathVariable Long universityId) {
        return ResponseEntity.ok().body(tuitionFeeService.getAverageTuitionFee(universityId));
    }
}
