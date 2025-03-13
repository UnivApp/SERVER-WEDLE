package yerong.wedle.community.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.community.service.CommunityService;
import yerong.wedle.school.dto.SchoolRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CommunityApiController {

    private final CommunityService communityService;

    @Operation(summary = "커뮤니티 생성", description = "학교 정보에 해당하는 커뮤니티가 없으면 새로 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "커뮤니티가 성공적으로 생성되었습니다."),
            @ApiResponse(responseCode = "404", description = "해당 학교를 찾을 수 없습니다."),
    })
    @PostMapping
    public ResponseEntity<String> createCommunity(SchoolRequest schoolRequest) {
        try {
            communityService.createCommunityIfNotExists(schoolRequest.getSchoolId());
            return ResponseEntity.ok("커뮤니티가 성공적으로 생성되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("학교 정보를 확인해 주세요.");
        }
    }
}
