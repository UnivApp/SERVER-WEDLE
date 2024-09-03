package yerong.wedle.category.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.category.event.domain.Event;
import yerong.wedle.university.domain.University;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByUniversity(University university);
}
