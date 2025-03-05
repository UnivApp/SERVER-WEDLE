package yerong.wedle.schoolcalendar.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.schoolcalendar.dto.SchoolCalendarBetweenDatesRequest;
import yerong.wedle.schoolcalendar.dto.SchoolCalendarByDateRequest;
import yerong.wedle.schoolcalendar.dto.TotalSchoolCalendarResponse;
import yerong.wedle.schoolcalendar.service.SchoolCalendarService;

@Tag(name = "School Calendar API", description = "학사 일정 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/school-calendar")
public class SchoolCalendarApiController {

    private final SchoolCalendarService schoolCalendarService;

    @Operation(
            summary = "학사 일정 조회 (단일 날짜)",
            description = "지정한 날짜의 학사 일정을 반환합니다."
    )
    @GetMapping("/list/date")
    public ResponseEntity<TotalSchoolCalendarResponse> getSchoolCalendarByDate(SchoolCalendarByDateRequest request) {
        TotalSchoolCalendarResponse scheduleByDate = schoolCalendarService.getScheduleByDate(request);
        return ResponseEntity.ok(scheduleByDate);
    }

    @Operation(
            summary = "학사 일정 조회 (기간)",
            description = "지정한 기간의 학사 일정을 반환합니다."
    )
    @GetMapping("/list/dates")
    public ResponseEntity<TotalSchoolCalendarResponse> getScheduleBetween(
            SchoolCalendarBetweenDatesRequest request) {
        TotalSchoolCalendarResponse scheduleBetween = schoolCalendarService.getScheduleBetween(request);
        return ResponseEntity.ok(scheduleBetween);
    }
}
