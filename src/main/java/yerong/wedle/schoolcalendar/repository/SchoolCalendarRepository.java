package yerong.wedle.schoolcalendar.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.school.domain.School;
import yerong.wedle.schoolcalendar.domain.SchoolCalendar;

public interface SchoolCalendarRepository extends JpaRepository<SchoolCalendar, Long> {
    List<SchoolCalendar> findBySchoolAndDateBetween(School school, LocalDate startDate, LocalDate endDate);

    List<SchoolCalendar> findBySchoolAndDate(School school, LocalDate date);
}
