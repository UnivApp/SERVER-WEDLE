package yerong.wedle.calendar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.calendar.domain.CalendarEvent;
import yerong.wedle.calendar.dto.CalendarEventResponse;
import yerong.wedle.calendar.exception.CalendarEventNotFoundException;
import yerong.wedle.calendar.repository.CalendarEventRepository;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.repository.MemberRepository;
import yerong.wedle.notification.domain.Notification;
import yerong.wedle.notification.exception.NotificationNotFoundException;
import yerong.wedle.notification.repository.NotificationRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CalendarEventService {

    private final CalendarEventRepository calendarEventRepository;
    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;

    public List<CalendarEventResponse> getAll() {
        List<CalendarEvent> calendarEvents = calendarEventRepository.findAll();

        return calendarEvents.stream()
                .flatMap(event -> convertToDto(event).stream())
                .collect(Collectors.toList());
    }

    public List<CalendarEventResponse> convertToDto(CalendarEvent calendarEvent) {
        LocalDate startDate = calendarEvent.getStartDate();
        LocalDate endDate = calendarEvent.getEndDate();

        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);


        Notification notification = notificationRepository.findByEventAndMember(calendarEvent, member);
        boolean notificationActive;
        if(notification != null) {
            notificationActive = notification.isActive();
        } else {
            notificationActive = false;
        }
        Long notificationId = notification != null ? notification.getNotificationId() : null;

        if (endDate == null) {
            return List.of(new CalendarEventResponse(
                    calendarEvent.getId(),
                    calendarEvent.getTitle(),
                    startDate,
                    calendarEvent.getCalendarEventType().getDisplayName(),
                    notificationActive,
                    notificationId
            ));
        }

        return startDate.datesUntil(endDate.plusDays(1))
                .map(date -> new CalendarEventResponse(
                        calendarEvent.getId(),
                        calendarEvent.getTitle(),
                        date,
                        calendarEvent.getCalendarEventType().getDisplayName(),
                        notificationActive,
                        notificationId
                ))
                .collect(Collectors.toList());
    }
    private String getCurrentUserId() {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();

        return socialId;
    }
}
