package yerong.wedle.notification.service;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import yerong.wedle.calendar.domain.CalendarEvent;
import yerong.wedle.calendar.exception.CalendarEventNotFoundException;
import yerong.wedle.calendar.repository.CalendarEventRepository;
import yerong.wedle.common.utils.FcmUtils;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.repository.MemberRepository;
import yerong.wedle.notification.domain.Notification;
import yerong.wedle.notification.dto.CreateNotificationRequest;
import yerong.wedle.notification.dto.NotificationResponse;
import yerong.wedle.notification.exception.DuplicateNotificationException;
import yerong.wedle.notification.exception.NotificationDateOutOfRangeException;
import yerong.wedle.notification.repository.NotificationRepository;

@Slf4j
@Service
@RequiredArgsConstructor

public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final CalendarEventRepository calendarEventRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public NotificationResponse createNotification(CreateNotificationRequest request) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);

        CalendarEvent calendarEvent = getCalendarEventById(request.getEventId());

        if (!isDateWithinEventRange(request.getNotificationDate(), calendarEvent)) {
            throw new NotificationDateOutOfRangeException();
        }

        if (notificationRepository.findByMemberAndEventAndNotificationDate(member, calendarEvent, request.getNotificationDate()).isPresent()) {
            throw new DuplicateNotificationException();
        }
        Notification notification = Notification.builder()
                .notificationDate(request.getNotificationDate())
                .event(calendarEvent)
                .registrationTokens(request.getRegistrationTokens())
                .isActive(true)
                .member(member)
                .build();
        notification = notificationRepository.save(notification);

        return convertToResponse(notification);
    }

    private boolean isDateWithinEventRange(LocalDate notificationDate, CalendarEvent calendarEvent) {
        LocalDate startDate = calendarEvent.getStartDate();
        LocalDate endDate = calendarEvent.getEndDate() != null ? calendarEvent.getEndDate() : startDate;
        return !notificationDate.isBefore(startDate) && !notificationDate.isAfter(endDate);
    }

    private CalendarEvent getCalendarEventById(Long calendarId) {
        return calendarEventRepository.findById(calendarId).orElseThrow(CalendarEventNotFoundException::new);
    }

    @Transactional
    @Scheduled(cron = "0 25 15 * * ?", zone = "Asia/Seoul")
    public void sendNotifications() {
        LocalDate today = LocalDate.now();
        List<Notification> dueNotifications = notificationRepository.findByNotificationDate(today);

        for (Notification notification : dueNotifications) {
            if (notification.isActive()) {
                List<String> registrationTokens = notification.getRegistrationTokens();
                String title = notification.getEvent().getTitle();
                String body = "일정 당일입니다.";

                FcmUtils.broadCast(registrationTokens, title, body);

                notification.setActive(false);
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
    @Transactional
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    @Transactional
    public List<NotificationResponse> getNotificationsByMember() {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);

        List<Notification> notifications = notificationRepository.findByMember(member);
        return notifications.stream()
                .map(this::convertToResponse)
                .toList();
    }
    private String getCurrentUserId() {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();

        return socialId;
    }
}
