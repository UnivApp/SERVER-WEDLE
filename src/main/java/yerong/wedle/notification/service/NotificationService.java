package yerong.wedle.notification.service;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import yerong.wedle.calendar.domain.CalendarEvent;
import yerong.wedle.calendar.exception.CalendarEventNotFoundException;
import yerong.wedle.calendar.repository.CalendarEventRepository;
import yerong.wedle.common.utils.FcmUtils;
import yerong.wedle.notification.domain.Notification;
import yerong.wedle.notification.dto.CreateNotificationRequest;
import yerong.wedle.notification.dto.NotificationResponse;
import yerong.wedle.notification.repository.NotificationRepository;

@Slf4j
@Service
@RequiredArgsConstructor

public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final CalendarEventRepository calendarEventRepository;

    @Transactional
    public NotificationResponse createNotification(CreateNotificationRequest request) {
        CalendarEvent calendarEvent = getCalendarEventById(request.getEventId());
        LocalDateTime notificationTime = request.getNotificationDate().atTime(10, 0);

        Notification notification = Notification.builder()
                .notificationTime(notificationTime)
                .event(calendarEvent)
                .registrationTokens(request.getRegistrationTokens())
                .isActive(true)
                .build();
        notification = notificationRepository.save(notification);

        return convertToResponse(notification);
    }

    public List<NotificationResponse> getNotificationsByTime(LocalDateTime time) {
        return notificationRepository.findByNotificationTime(time)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private CalendarEvent getCalendarEventById(Long calendarId) {
        return calendarEventRepository.findById(calendarId).orElseThrow(CalendarEventNotFoundException::new);
    }

    @Transactional
    @Scheduled(cron = "0 0 10 * * ?")
    public void sendNotifications() {
        LocalDate today = LocalDate.now();
        LocalDateTime notificationTime = today.atTime(10, 0);
        List<Notification> dueNotifications = notificationRepository.findByNotificationTime(notificationTime);

        for (Notification notification : dueNotifications) {
            if (notification.isActive()) {
                List<String> registrationTokens = notification.getRegistrationTokens();
                String title = notification.getEvent().getTitle();
                String body = "";

                FcmUtils.broadCast(registrationTokens, title, body);

                notification.setActive(false);
                notification.getEvent().setNotified(true);
                log.info("알람이 울렸습니다.");
            }
        }
    }

    private NotificationResponse convertToResponse(Notification notification) {
        return NotificationResponse.builder()
                .notificationId(notification.getNotificationId())
                .notificationTime(notification.getNotificationTime())
                .eventId(notification.getEvent().getId())
                .registrationTokens(notification.getRegistrationTokens())
                .isActive(notification.isActive())
                .build();
    }

}
