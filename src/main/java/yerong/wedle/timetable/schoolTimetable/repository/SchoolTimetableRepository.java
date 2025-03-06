package yerong.wedle.timetable.schoolTimetable.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.member.domain.Member;
import yerong.wedle.timetable.schoolTimetable.domain.SchoolTimetable;

public interface SchoolTimetableRepository extends JpaRepository<SchoolTimetable, Long> {
    Optional<SchoolTimetable> findByMember(Member member);
}
