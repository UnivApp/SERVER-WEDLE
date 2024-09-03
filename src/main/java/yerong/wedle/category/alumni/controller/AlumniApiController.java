package yerong.wedle.category.alumni.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.category.alumni.dto.AlumniResponse;
import yerong.wedle.category.alumni.service.AlumniService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/alumni")
public class AlumniApiController {
    private final AlumniService alumniService;

    @GetMapping
    public ResponseEntity<List<AlumniResponse>> getAlumniByUniversityName(@RequestParam String universityName) {
        List<AlumniResponse> alumni = alumniService.getAlumniByUniversityName(universityName);
        return ResponseEntity.ok(alumni);
    }
}