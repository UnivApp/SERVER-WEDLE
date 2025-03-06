package yerong.wedle.timetable.personalTimetable.repository;

import java.time.LocalTime;
import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.timetable.DayOfWeek;
import yerong.wedle.timetable.personalTimetable.domain.PersonalSchedule;
import yerong.wedle.timetable.personalTimetable.domain.PersonalTimetable;

public interface PersonalScheduleRepository extends JpaRepository<PersonalSchedule, Long> {
    boolean existsByPersonalTimetableAndDayOfWeekAndStartTimeBeforeAndEndTimeAfter(PersonalTimetable personalTimetable,
                                                                                   DayOfWeek dayOfWeek,
                                                                                   LocalTime endTime,
                                                                                   LocalTime startTime);
}
