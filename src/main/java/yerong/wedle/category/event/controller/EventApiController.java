package yerong.wedle.category.event.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.category.event.dto.EventResponse;
import yerong.wedle.category.event.service.EventService;

import java.util.List;

@Tag(name = "Event API", description = "대학교 관련 이벤트 정보 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/events")
public class EventApiController {
    private final EventService eventService;

    @Operation(
            summary = "대학교 이벤트 조회",
            description = "대학교 ID를 이용해 해당 대학교에서 진행된 이벤트 목록을 조회합니다."
    )
    @GetMapping
    public ResponseEntity<List<EventResponse>> getEventsByUniversityId(@RequestParam Long universityId) {
        List<EventResponse> events = eventService.getEventsByUniversityId(universityId);
        return ResponseEntity.ok(events);
    }
}