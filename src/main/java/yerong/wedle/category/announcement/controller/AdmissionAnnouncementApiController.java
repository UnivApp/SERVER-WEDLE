package yerong.wedle.category.announcement.controller;

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
import yerong.wedle.category.announcement.dto.AdmissionAnnouncementResponse;
import yerong.wedle.category.announcement.service.AdmissionAnnouncementService;

import java.util.List;

@Tag(name = "AdmissionAnnouncement API", description = "대학교 입시설명회 및 연계 활동 공지 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/announcements")
public class AdmissionAnnouncementApiController {

    private final AdmissionAnnouncementService admissionAnnouncementService;


    @Operation(summary = "대학별 공지사항 조회", description = "특정 대학의 ID를 사용하여 해당 대학과 관련된 모든 공지사항을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "대학과 관련된 공지사항 조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 대학을 찾을 수 없음")
    })
    @GetMapping("/university/{universityId}")
    public ResponseEntity<List<AdmissionAnnouncementResponse>> getAnnouncementsByUniversity(@PathVariable Long universityId) {
        return ResponseEntity.ok().body(admissionAnnouncementService.getAnnouncementsByUniversity(universityId));
    }

    @Operation(summary = "카테고리별 공지사항 조회", description = "특정 카테고리를 사용하여 해당 카테고리에 속한 모든 공지사항을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리별 공지사항 조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 카테고리에 공지사항이 없음")
    })
    @GetMapping("/category")
    public ResponseEntity<List<AdmissionAnnouncementResponse>> getAnnouncementsByCategory(@RequestParam String category) {
        return ResponseEntity.ok().body(admissionAnnouncementService.getAnnouncementsByCategory(category));
    }

    @Operation(summary = "모든 공지사항 조회", description = "모든 공지사항을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모든 공지사항 조회 성공")
    })
    @GetMapping
    public ResponseEntity<List<AdmissionAnnouncementResponse>> getAllAnnouncements() {
        return ResponseEntity.ok().body(admissionAnnouncementService.getAllAnnouncements());
    }
}
