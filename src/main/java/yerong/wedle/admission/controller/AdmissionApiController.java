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
            summary = "경쟁률만 조회",
            description = "모든 대학의 경쟁률만 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "경쟁률 조회 성공"),
            @ApiResponse(responseCode = "404", description = "입결 정보를 찾을 수 없습니다.")
    })
    @GetMapping("/competition-rates")
    public ResponseEntity<List<AdmissionResponse>> getCompetitionRates() {
        List<AdmissionResponse> responses = admissionService.getCompetitionRates();
        return ResponseEntity.ok(responses);
    }

    @Operation(
            summary = "취업률만 조회",
            description = "모든 대학의 취업률만 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "취업률 조회 성공"),
            @ApiResponse(responseCode = "404", description = "입결 정보를 찾을 수 없습니다.")
    })
    @GetMapping("/employment-rates")
    public ResponseEntity<List<AdmissionResponse>> getEmploymentRates() {
        List<AdmissionResponse> responses = admissionService.getEmploymentRates();
        return ResponseEntity.ok(responses);
    }

    @Operation(
            summary = "경쟁률 + 취업률 조회",
            description = "모든 대학의 경쟁률과 취업률을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "경쟁률 및 취업률 조회 성공"),
            @ApiResponse(responseCode = "404", description = "입결 정보를 찾을 수 없습니다.")
    })
    @GetMapping("/competition-employment-rates")
    public ResponseEntity<List<AdmissionResponse>> getCompetitionAndEmploymentRates() {
        List<AdmissionResponse> responses = admissionService.getCompetitionAndEmploymentRates();
        return ResponseEntity.ok(responses);
    }

    @Operation(
            summary = "경쟁률 + 모집자수 + 지원자수 조회",
            description = "모든 대학의 경쟁률, 모집자수, 지원자수를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "경쟁률, 모집자수, 지원자수 조회 성공"),
            @ApiResponse(responseCode = "404", description = "입결 정보를 찾을 수 없습니다.")
    })
    @GetMapping("/competition-numbers")
    public ResponseEntity<List<AdmissionResponse>> getCompetitionAndNumbers() {
        List<AdmissionResponse> responses = admissionService.getCompetitionAndNumbers();
        return ResponseEntity.ok(responses);
    }
}
