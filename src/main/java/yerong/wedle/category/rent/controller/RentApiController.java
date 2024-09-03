package yerong.wedle.category.rent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.category.rent.dto.RentResponse;
import yerong.wedle.category.rent.service.RentService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rents")
public class RentApiController {

    private final RentService rentService;

    @GetMapping
    public ResponseEntity<List<RentResponse>> getRentsByUniversityName(@RequestParam String universityName) {
        List<RentResponse> rents = rentService.getRentsByUniversityName(universityName);
        return ResponseEntity.ok(rents);
    }
}
