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

        Notification notification = Notification.builder()
                .notificationDate(request.getNotificationDate())
                .event(calendarEvent)
                .registrationTokens(request.getRegistrationTokens())
                .isActive(true)
                .build();
        notification = notificationRepository.save(notification);

        return convertToResponse(notification);
    }

    public List<NotificationResponse> getNotificationsByDate(LocalDate date) {
        return notificationRepository.findByNotificationDate(date)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private CalendarEvent getCalendarEventById(Long calendarId) {
        return calendarEventRepository.findById(calendarId).orElseThrow(CalendarEventNotFoundException::new);
    }

    @Transactional
    @Scheduled(cron = "0 51 16 * * ?")
    public void sendNotifications() {
        LocalDate today = LocalDate.now();
        List<Notification> dueNotifications = notificationRepository.findByNotificationDate(today);

        for (Notification notification : dueNotifications) {
            if (notification.isActive()) {
                List<String> registrationTokens = notification.getRegistrationTokens();
                String title = notification.getEvent().getTitle();
                String body = "";

                FcmUtils.broadCast(registrationTokens, title, body);

                notification.setActive(false);
                log.info("알람이 울렸습니다.");
            }
        }
    }

    private NotificationResponse convertToResponse(Notification notification) {
        return NotificationResponse.builder()
                .notificationId(notification.getNotificationId())
                .notificationDate(notification.getNotificationDate())
                .eventId(notification.getEvent().getId())
                .registrationTokens(notification.getRegistrationTokens())
                .isActive(notification.isActive())
                .build();
    }

}
