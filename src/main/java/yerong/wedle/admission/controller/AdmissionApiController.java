package yerong.wedle.admission.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.admission.dto.AdmissionResponse;
import yerong.wedle.admission.service.AdmissionService;

import java.util.List;

@Tag(name = "Admission API", description = "입결 정보 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admissions")
public class AdmissionApiController {

    private final AdmissionService admissionService;


    @Operation(
            summary = "수시 입결 1~4위 조회",
            description = "수시 입결에서 상위 1~4위 대학 정보를 순위 기준으로 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수시 입결 1~4위 조회 성공"),
            @ApiResponse(responseCode = "404", description = "입결 정보를 찾을 수 없습니다.")
    })
    @GetMapping("/early/top4")
    public ResponseEntity<List<AdmissionResponse>> getTop4EarlyAdmissions() {
        List<AdmissionResponse> responses = admissionService.getTop4EarlyAdmissions();
        return ResponseEntity.ok(responses);
    }

    @Operation(
            summary = "정시 입결 1~4위 조회",
            description = "정시 입결에서 상위 1~4위 대학 정보를 순위 기준으로 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정시 입결 1~4위 조회 성공"),
            @ApiResponse(responseCode = "404", description = "입결 정보를 찾을 수 없습니다.")
    })
    @GetMapping("/regular/top4")
    public ResponseEntity<List<AdmissionResponse>> getTop4RegularAdmissions() {
        List<AdmissionResponse> responses = admissionService.getTop4RegularAdmissions();
        return ResponseEntity.ok(responses);
    }

    @Operation(
            summary = "수시 전체 입결 조회",
            description = "수시 입결에 해당하는 모든 대학 정보를 순위 기준으로 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수시 전체 입결 조회 성공"),
            @ApiResponse(responseCode = "404", description = "입결 정보를 찾을 수 없습니다.")
    })
    @GetMapping("/early/all")
    public ResponseEntity<List<AdmissionResponse>> getAllEarlyAdmissions() {
        List<AdmissionResponse> responses = admissionService.getAllEarlyAdmissions();
        return ResponseEntity.ok(responses);
    }

    @Operation(
            summary = "정시 전체 입결 조회",
            description = "정시 입결에 해당하는 모든 대학 정보를 순위 기준으로 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정시 전체 입결 조회 성공"),
            @ApiResponse(responseCode = "404", description = "입결 정보를 찾을 수 없습니다.")
    })
    @GetMapping("/regular/all")
    public ResponseEntity<List<AdmissionResponse>> getAllRegularAdmissions() {
        List<AdmissionResponse> responses = admissionService.getAllRegularAdmissions();
        return ResponseEntity.ok(responses);
    }
}
