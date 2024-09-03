package yerong.wedle.banner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.banner.dto.BannerResponse;
import yerong.wedle.banner.service.BannerService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/banners")
public class BannerApiController {

    private final BannerService bannerService;

    @GetMapping
    public ResponseEntity<?> getBanners() {
        List<BannerResponse> banners = bannerService.getAllBanners();
        return ResponseEntity.ok(banners);
    }
}
