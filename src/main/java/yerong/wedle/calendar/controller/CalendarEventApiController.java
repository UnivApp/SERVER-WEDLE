package yerong.wedle.calendar.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.calendar.dto.CalendarEventResponse;
import yerong.wedle.calendar.exception.CalendarEventNotFoundException;
import yerong.wedle.calendar.service.CalendarEventService;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "CalendarEvent API", description = "달력 이벤트 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/calendar-events")
public class CalendarEventApiController {

    private final CalendarEventService calendarEventService;

    @Operation(summary = "이벤트 ID로 조회", description = "특정 이벤트의 ID를 사용하여 해당 이벤트의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이벤트 조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 ID의 이벤트를 찾을 수 없음")
    })
    @GetMapping("/{eventId}")
    public ResponseEntity<CalendarEventResponse> getEventById(@PathVariable Long eventId) {
        try {
            CalendarEventResponse response = calendarEventService.getEventById(eventId);
            return ResponseEntity.ok(response);
        } catch (CalendarEventNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "모든 이벤트 조회", description = "모든 달력 이벤트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모든 이벤트 조회 성공")
    })
    @GetMapping
    public ResponseEntity<List<CalendarEventResponse>> getAllEvents() {
        List<CalendarEventResponse> responses = calendarEventService.getAllEvents();
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "날짜로 이벤트 조회", description = "특정 날짜에 해당하는 이벤트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "날짜로 이벤트 조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 날짜에 이벤트가 없음")
    })
    @GetMapping("/by-date")
    public ResponseEntity<List<CalendarEventResponse>> getEventsByDate(@RequestParam LocalDate date) {
        List<CalendarEventResponse> responses = calendarEventService.getEventsByDate(date);
        if (responses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(responses);
    }
}
