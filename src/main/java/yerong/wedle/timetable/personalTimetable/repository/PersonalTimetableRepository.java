package yerong.wedle.timetable.personalTimetable.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.member.domain.Member;
import yerong.wedle.timetable.personalTimetable.domain.PersonalTimetable;

public interface PersonalTimetableRepository extends JpaRepository<PersonalTimetable, Long> {
    Optional<PersonalTimetable> findByMember(Member member);
}
