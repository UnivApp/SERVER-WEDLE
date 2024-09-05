package yerong.wedle.category.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.category.event.domain.EventImage;

public interface EventImageRepository extends JpaRepository<EventImage, Long> {
}
