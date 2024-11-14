package yerong.wedle.calendar.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.calendar.domain.CalendarEvent;
import yerong.wedle.calendar.dto.CalendarEventResponse;
import yerong.wedle.calendar.repository.CalendarEventRepository;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.repository.MemberRepository;
import yerong.wedle.notification.domain.Notification;
import yerong.wedle.notification.repository.NotificationRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CalendarEventService {

    private final CalendarEventRepository calendarEventRepository;
    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;

    public List<CalendarEventResponse> getAll() {
        List<CalendarEvent> calendarEvents = calendarEventRepository.findAll();
        return convertToDto(calendarEvents);
    }

    public List<CalendarEventResponse> convertToDto(List<CalendarEvent> calendarEvents) {
        List<CalendarEventResponse> calendarEventResponses = new ArrayList<>();
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);
        for (CalendarEvent calendarEvent : calendarEvents) {
            LocalDate startDate = calendarEvent.getStartDate();
            LocalDate endDate = calendarEvent.getEndDate();

            if (endDate == null) {
                Notification notification = notificationRepository.findByMemberAndEventAndNotificationDate(member,
                        calendarEvent, startDate).orElse(null);
                Long notificationId = notification != null ? notification.getNotificationId() : null;
                calendarEventResponses.add(new CalendarEventResponse(
                        calendarEvent.getId(),
                        calendarEvent.getTitle(),
                        startDate,
                        calendarEvent.getCalendarEventType().getDisplayName(),
                        notification != null && notification.isActive() && notification.getNotificationDate()
                                .equals(startDate),
                        notificationId
                ));
            } else {
                startDate.datesUntil(endDate.plusDays(1))
                        .forEach(date -> {
                            Notification notification = notificationRepository.findByMemberAndEventAndNotificationDate(
                                    member, calendarEvent, date).orElse(null);
                            Long notificationId = notification != null ? notification.getNotificationId() : null;
                            boolean isActive = notification != null && notification.isActive()
                                    && notification.getNotificationDate().equals(date);

                            calendarEventResponses.add(new CalendarEventResponse(
                                    calendarEvent.getId(),
                                    calendarEvent.getTitle(),
                                    date,
                                    calendarEvent.getCalendarEventType().getDisplayName(),
                                    isActive,
                                    notificationId
                            ));
                        });
            }
        }
        return calendarEventResponses;
    }

    private String getCurrentUserId() {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();

        return socialId;
    }
}
