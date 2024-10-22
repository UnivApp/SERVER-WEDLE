package yerong.wedle.calendar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.calendar.domain.CalendarEvent;
import yerong.wedle.calendar.dto.CalendarEventResponse;
import yerong.wedle.calendar.exception.CalendarEventNotFoundException;
import yerong.wedle.calendar.repository.CalendarEventRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CalendarEventService {

    private final CalendarEventRepository calendarEventRepository;

    public List<CalendarEventResponse> getAll() {
        List<CalendarEvent> calendarEvents = calendarEventRepository.findAll();

        return calendarEvents.stream()
                .flatMap(event -> convertToDto(event).stream())
                .collect(Collectors.toList());
    }

    public List<CalendarEventResponse> convertToDto(CalendarEvent calendarEvent) {
        LocalDate startDate = calendarEvent.getStartDate();
        LocalDate endDate = calendarEvent.getEndDate();

        if (endDate == null) {
            return List.of(new CalendarEventResponse(
                    calendarEvent.getId(),
                    calendarEvent.getTitle(),
                    startDate,
                    calendarEvent.getCalendarEventType().getDisplayName(),
                    calendarEvent.isNotified()
            ));
        }

        return startDate.datesUntil(endDate.plusDays(1))
                .map(date -> new CalendarEventResponse(
                        calendarEvent.getId(),
                        calendarEvent.getTitle(),
                        date,
                        calendarEvent.getCalendarEventType().getDisplayName(),
                        calendarEvent.isNotified()
                ))
                .collect(Collectors.toList());
    }

}
