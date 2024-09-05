package yerong.wedle.category.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.category.event.dto.EventResponse;
import yerong.wedle.category.event.service.EventService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/events")
public class EventApiController {
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventResponse>> getEventsByUniversityName(@RequestParam String universityName) {
        List<EventResponse> events = eventService.getEventsByUniversityName(universityName);
        return ResponseEntity.ok(events);
    }
}