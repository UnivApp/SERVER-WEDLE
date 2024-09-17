package yerong.wedle.department.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.department.domain.DepartmentType;
import yerong.wedle.department.dto.DepartmentResponse;
import yerong.wedle.department.service.DepartmentService;

import java.util.List;

@Tag(name = "Department API", description = "대학교 학과 정보 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/departments")
public class DepartmentApiController {

    private final DepartmentService departmentService;

    @Operation(summary = "계열별 학과 조회", description = "특정 학교의 계열별 학과 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "계열별 학과 목록 조회 성공"),
            @ApiResponse(responseCode = "404", description = "학교를 찾을 수 없음")
    })
    @GetMapping("/by-type/{universityId}/{departmentType}")
    public List<DepartmentResponse> getDepartmentNamesByType(
            @PathVariable Long universityId,
            @PathVariable DepartmentType departmentType) {
        return departmentService.getDepartmentNamesByType(universityId, departmentType);
    }

    @Operation(summary = "학교별 모든 학과 조회", description = "특정 학교의 모든 학과 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "학교별 모든 학과 목록 조회 성공"),
            @ApiResponse(responseCode = "404", description = "학교를 찾을 수 없음")
    })
    @GetMapping("/all/{universityId}")
    public List<DepartmentResponse> getAllDepartmentNames(
            @PathVariable Long universityId) {
        return departmentService.getAllDepartmentNames(universityId);
    }
}
