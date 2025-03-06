package yerong.wedle.timetable.personalTimetable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.timetable.personalTimetable.domain.PersonalSchedule;

public interface PersonalScheduleRepository extends JpaRepository<PersonalSchedule, Long> {
}
