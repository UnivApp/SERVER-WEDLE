package yerong.wedle.category.activity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.category.activity.domain.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
