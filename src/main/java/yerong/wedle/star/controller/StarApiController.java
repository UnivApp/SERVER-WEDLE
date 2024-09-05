package yerong.wedle.star.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.oauth.princiapl.PrincipalDetails;
import yerong.wedle.star.service.StarService;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/stars")
public class StarApiController {

    private final StarService starService;

    @PostMapping("/add")
    public ResponseEntity<Void> addStar(
            @RequestParam String universityName
            ) {

        log.info("========================");
        log.info("universityName : " + universityName);
        log.info("========================");

        starService.addStar(universityName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove")
    public ResponseEntity<Void> removeStar(@RequestParam String universityName,
                                           @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Long memberId = principalDetails.getMember().getMemberId();

        starService.removeStar(universityName);
        return ResponseEntity.ok().build();
    }
}
