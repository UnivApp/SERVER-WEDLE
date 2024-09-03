package yerong.wedle.banner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.banner.domain.Banner;

public interface BannerRepository extends JpaRepository<Banner, Long> {
}
