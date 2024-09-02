package yerong.wedle.category.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.category.activity.domain.Activity;
import yerong.wedle.category.event.domain.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
