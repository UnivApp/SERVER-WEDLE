package yerong.wedle.star.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.star.service.StarService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/stars")
public class StarApiController {

    private final StarService starService;

    @PostMapping("/add")
    public ResponseEntity<Void> addStar(@RequestParam Long memberId, @RequestParam String universityName) {
        starService.addStar(memberId, universityName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove")
    public ResponseEntity<Void> removeStar(@RequestParam Long memberId, @RequestParam String universityName) {
        starService.removeStar(memberId, universityName);
        return ResponseEntity.ok().build();
    }
}
