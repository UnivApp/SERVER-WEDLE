package yerong.wedle.notification.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.calendar.domain.CalendarEvent;
import yerong.wedle.member.domain.Member;
import yerong.wedle.notification.domain.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByNotificationDate(LocalDate notification);

    Notification findByEventAndMember(CalendarEvent calendarEvent, Member member);

    List<Notification> findByMember(Member member);
}
