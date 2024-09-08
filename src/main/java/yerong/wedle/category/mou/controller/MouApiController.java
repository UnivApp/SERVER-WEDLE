package yerong.wedle.category.mou.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.category.mou.dto.MouResponse;
import yerong.wedle.category.mou.service.MouService;

import java.util.List;

@Tag(name = "MOU API", description = "대학교와의 MOU 정보 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mous")
public class MouApiController {

    private final MouService mouService;

    @Operation(
            summary = "대학교 MOU 조회", description = "대학교 이름을 이용해 해당 대학교와 체결된 MOU 목록을 조회합니다."
    )
    @GetMapping
    public ResponseEntity<List<MouResponse>> getMousByUniversityName(@RequestParam String universityName) {
        List<MouResponse> mous = mouService.getMousByUniversityName(universityName);
        return ResponseEntity.ok(mous);
    }
}
