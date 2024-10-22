package yerong.wedle.notification.service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import yerong.wedle.calendar.domain.CalendarEvent;
import yerong.wedle.calendar.exception.CalendarEventNotFoundException;
import yerong.wedle.calendar.repository.CalendarEventRepository;
import yerong.wedle.common.utils.FcmUtils;
import yerong.wedle.notification.domain.Notification;
import yerong.wedle.notification.dto.CreateNotificationRequest;
import yerong.wedle.notification.repository.NotificationRepository;

@Service
@RequiredArgsConstructor

public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final CalendarEventRepository calendarEventRepository;

    @Transactional
    public Notification createNotification(CreateNotificationRequest request) {
        CalendarEvent calendarEvent = getCalendarEventById(request.getEventId());

        boolean isActive = !calendarEvent.isNotified();

        Notification notification = Notification.builder()
                .notificationTime(LocalDateTime.now())
                .event(calendarEvent)
                .registrationTokens(request.getRegistrationTokens())
                .isActive(true)
                .build();

        return notificationRepository.save(notification);
    }

    public List<Notification> getNotificationsByTime(LocalDateTime time) {
        return notificationRepository.findByNotificationTime(time);
    }

    private CalendarEvent getCalendarEventById(Long calendarId) {
        return calendarEventRepository.findById(calendarId).orElseThrow(CalendarEventNotFoundException::new);
    }
    @Transactional
    @Scheduled(fixedRate = 60000)
    public void sendNotifications() {
        LocalDateTime now = LocalDateTime.now();
        List<Notification> dueNotifications = notificationRepository.findByNotificationTime(now);

        for (Notification notification : dueNotifications) {
            if (notification.isActive()) {
                List<String> registrationTokens = notification.getRegistrationTokens();
                String title = notification.getEvent().getTitle();
                String body = "";

                FcmUtils.broadCast(registrationTokens, title, body);

                notification.setActive(false);
                notification.getEvent().setNotified(true);
            }
        }
    }
}
