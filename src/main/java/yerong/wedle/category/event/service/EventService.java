package yerong.wedle.category.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.category.event.domain.Event;
import yerong.wedle.category.event.dto.EventImageResponse;
import yerong.wedle.category.event.dto.EventResponse;
import yerong.wedle.category.event.repository.EventImageRepository;
import yerong.wedle.category.event.repository.EventRepository;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.exception.UniversityNotFoundException;
import yerong.wedle.university.repository.UniversityRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UniversityRepository universityRepository;

    @Transactional
    public List<EventResponse> getEventsByUniversityId(Long universityId) {
        University university = universityRepository.findById(universityId)
                .orElseThrow(UniversityNotFoundException::new);
        List<Event> events = eventRepository.findByUniversity(university);
        return events.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    private EventResponse convertToDto(Event event) {
        return new EventResponse(
                event.getName(),
                event.getEventType().getDisplayName(),
                event.getStartDate(),
                event.getEndDate(),
                event.getEventDetails() != null ? event.getEventDetails().getLineUp() : null,
                event.getEventDetails() != null ? event.getEventDetails().getDescriptions() : null,
                event.getPhotos().stream()
                        .map(photo -> new EventImageResponse(photo.getImageUrl(), photo.getSource()))
                        .collect(Collectors.toSet())
        );
    }
}
