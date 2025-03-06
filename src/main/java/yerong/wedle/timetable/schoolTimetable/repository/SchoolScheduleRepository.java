package yerong.wedle.timetable.schoolTimetable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.timetable.schoolTimetable.domain.SchoolSchedule;

public interface SchoolScheduleRepository extends JpaRepository<SchoolSchedule, Long> {
}
