package yerong.wedle.timetable.personalTimetable.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.timetable.personalTimetable.dto.PersonalScheduleRequest;
import yerong.wedle.timetable.personalTimetable.dto.PersonalScheduleResponse;
import yerong.wedle.timetable.personalTimetable.dto.PersonalTimetableResponse;
import yerong.wedle.timetable.personalTimetable.service.PersonalTimetableService;

@Tag(name = "Personal-Timetable API", description = "개인 시간표 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/personal-timetable")
public class PersonalTimetableApiController {
    private final PersonalTimetableService personalTimetableService;

    @Operation(
            summary = "개인 시간표 조회",
            description = "생성한 개인 시간표를 반환합니다."
    )
    @GetMapping()
    public ResponseEntity<PersonalTimetableResponse> getPersonalTimetable() {
        PersonalTimetableResponse personalTimetable = personalTimetableService.getPersonalTimetable();
        return ResponseEntity.ok(personalTimetable);
    }

    @Operation(
            summary = "개인 시간표 생성",
            description = "개인 시간표를 생성합니다."
    )
    @PostMapping("/create")
    public ResponseEntity<String> createPersonalTimetable() {
        personalTimetableService.createPersonalTimetable();
        return ResponseEntity.ok("개인 시간표가 성공적으로 생성되었습니다.");
    }

    @Operation(
            summary = "시간표 비우기",
            description = "시간표 내의 모든 스케줄을 삭제합니다."
    )
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearPersonalTimetable() {
        personalTimetableService.clearTimetable();
        return ResponseEntity.ok("개인 시간표의 모든 스케줄이 삭제되었습니다.");
    }

    @Operation(
            summary = "스케줄 생성",
            description = "개인 시간표에 새로운 스케줄을 추가합니다."
    )
    @PostMapping("/{timetableId}/schedule")
    public ResponseEntity<String> createSchedule(@RequestBody PersonalScheduleRequest request) {
        personalTimetableService.createSchedule(request);
        return ResponseEntity.ok("개인 스케줄이 성공적으로 생성되었습니다.");
    }

    @Operation(
            summary = "스케줄 삭제",
            description = "주어진 ID의 스케줄을 삭제합니다."
    )
    @DeleteMapping("/schedule/{scheduleId}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Long scheduleId) {
        personalTimetableService.deleteSchedule(scheduleId);
        return ResponseEntity.ok("개인 스케줄이 삭제되었습니다.");
    }

    @Operation(
            summary = "스케줄 조회",
            description = "주어진 ID의 스케줄을 조회합니다."
    )
    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<PersonalScheduleResponse> getScheduleById(@PathVariable Long scheduleId) {
        PersonalScheduleResponse response = personalTimetableService.getScheduleById(scheduleId);
        return ResponseEntity.ok(response);
    }
}
