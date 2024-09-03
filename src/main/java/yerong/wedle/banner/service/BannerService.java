package yerong.wedle.banner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.banner.domain.Banner;
import yerong.wedle.banner.dto.BannerResponse;
import yerong.wedle.banner.repository.BannerRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BannerService {

    private final BannerRepository bannerRepository;

    @Transactional(readOnly = true)
    public List<BannerResponse> getAllBanners() {
        List<Banner> banners = bannerRepository.findAll();
        return banners.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private BannerResponse convertToDto(Banner banner) {
        return new BannerResponse(
                banner.getImageUrl(),
                banner.getLinkUrl(),
                banner.getAltText()
        );
    }
}
