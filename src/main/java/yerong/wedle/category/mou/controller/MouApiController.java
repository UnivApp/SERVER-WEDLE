package yerong.wedle.category.mou.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.category.mou.dto.MouResponse;
import yerong.wedle.category.mou.service.MouService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mous")
public class MouApiController {

    private final MouService mouService;

    @GetMapping
    public ResponseEntity<List<MouResponse>> getMousByUniversityName(@RequestParam String universityName) {
        List<MouResponse> mous = mouService.getMousByUniversityName(universityName);
        return ResponseEntity.ok(mous);
    }
}
