package yerong.wedle.category.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.category.event.domain.Event;
import yerong.wedle.category.event.dto.EventResponse;
import yerong.wedle.category.event.repository.EventRepository;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.repository.UniversityRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UniversityRepository universityRepository;

    @Transactional
    public List<EventResponse> getEventsByUniversityName(String universityName) {
        University university = universityRepository.findByName(universityName)
                .orElseThrow(() -> new IllegalArgumentException("대학교를 찾을 수 없습니다."));
        List<Event> events = eventRepository.findByUniversity(university);
        return events.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    private EventResponse convertToDto(Event event) {
        return new EventResponse(
                event.getName(),
                event.getDescription(),
                event.getStartDate(),
                event.getEndDate(),
                event.getLocation()
        );
    }
}
