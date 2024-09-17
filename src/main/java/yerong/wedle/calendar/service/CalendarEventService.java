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

    public CalendarEventResponse getEventById(Long eventId) {
        CalendarEvent event = calendarEventRepository.findById(eventId)
                .orElseThrow(CalendarEventNotFoundException::new);
        return convertToDto(event);
    }

    public List<CalendarEventResponse> getAllEvents() {
        return calendarEventRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<CalendarEventResponse> getEventsByDate(LocalDate date) {
        return calendarEventRepository.findByDate(date).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private CalendarEventResponse convertToDto(CalendarEvent event) {
        return new CalendarEventResponse(
                event.getId(),
                event.getDate(),
                event.getContent()
        );
    }
}
