package yerong.wedle.notification.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.notification.domain.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByNotificationDate(LocalDate notification);
}
