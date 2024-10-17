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

    @Operation(summary = "모든 이벤트 조회", description = "모든 달력 이벤트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모든 이벤트 조회 성공")
    })
    @GetMapping
    public ResponseEntity<List<CalendarEventResponse>> getAllEvents() {
        List<CalendarEventResponse> responses = calendarEventService.getAll();
        return ResponseEntity.ok(responses);
    }

}
