package yerong.wedle.report.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.report.dto.ReportRequest;
import yerong.wedle.report.service.ReportService;

@Tag(name = "Report API", description = "신고 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
public class ReportApiController {
    private final ReportService reportService;

    @Operation(summary = "신고 생성", description = "신고를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "신고가 성공적으로 생성되었습니다."),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음"),
            @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없음"),
            @ApiResponse(responseCode = "409", description = "이미 신고를 했음")
    })
    @PostMapping
    public ResponseEntity<String> createReport(@RequestBody ReportRequest reportRequest) {
        reportService.createReport(reportRequest);
        return ResponseEntity.ok("Report가 성공적으로 생성되었습니다.");
    }
}
