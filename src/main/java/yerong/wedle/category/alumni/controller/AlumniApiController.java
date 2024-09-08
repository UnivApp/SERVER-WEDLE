package yerong.wedle.category.alumni.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.category.alumni.dto.AlumniResponse;
import yerong.wedle.category.alumni.service.AlumniService;

import java.util.List;

@Tag(name = "Alumni API", description = "대학교 졸업생 정보 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/alumni")
public class AlumniApiController {
    private final AlumniService alumniService;

    @Operation(
            summary = "대학교 졸업생 조회", description = "대학교 이름을 이용해 해당 대학교의 졸업생 목록을 조회합니다."
    )
    @GetMapping
    public ResponseEntity<List<AlumniResponse>> getAlumniByUniversityName(@RequestParam String universityName) {
        List<AlumniResponse> alumni = alumniService.getAlumniByUniversityName(universityName);
        return ResponseEntity.ok(alumni);
    }
}