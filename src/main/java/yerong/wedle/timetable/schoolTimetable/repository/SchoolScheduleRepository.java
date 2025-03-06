package yerong.wedle.timetable.schoolTimetable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.timetable.DayOfWeek;
import yerong.wedle.timetable.schoolTimetable.domain.SchoolSchedule;
import yerong.wedle.timetable.schoolTimetable.domain.SchoolTimetable;

public interface SchoolScheduleRepository extends JpaRepository<SchoolSchedule, Long> {
    boolean existsBySchoolTimetableAndDayOfWeekAndPeriod(SchoolTimetable schoolTimetable, DayOfWeek dayOfWeek,
                                                         int period);
}
