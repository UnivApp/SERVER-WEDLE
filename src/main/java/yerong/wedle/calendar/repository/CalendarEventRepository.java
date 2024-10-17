package yerong.wedle.calendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.calendar.domain.CalendarEvent;

import java.time.LocalDate;
import java.util.List;

public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Long> {
}
