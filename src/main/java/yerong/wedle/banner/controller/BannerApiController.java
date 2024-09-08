package yerong.wedle.banner.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.banner.dto.BannerResponse;
import yerong.wedle.banner.service.BannerService;

import java.util.List;

@Tag(name = "Banner API", description = "배너 정보 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/banners")
public class BannerApiController {

    private final BannerService bannerService;
    @Operation(
            summary = "배너 조회", description = "모든 배너 목록을 조회합니다."
    )
    @GetMapping
    public ResponseEntity<?> getBanners() {
        List<BannerResponse> banners = bannerService.getAllBanners();
        return ResponseEntity.ok(banners);
    }
}
