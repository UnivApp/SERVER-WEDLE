package yerong.wedle.timetable.schoolTimetable.controller;

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
import yerong.wedle.timetable.schoolTimetable.dto.SchoolScheduleRequest;
import yerong.wedle.timetable.schoolTimetable.dto.SchoolScheduleResponse;
import yerong.wedle.timetable.schoolTimetable.dto.SchoolTimetableResponse;
import yerong.wedle.timetable.schoolTimetable.service.SchoolTimetableService;

@Tag(name = "School-Timetable API", description = "학교 시간표 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/school-timetable")
public class SchoolTimetableApiController {
    private final SchoolTimetableService schoolTimetableService;

    @Operation(
            summary = "학교 시간표 조회",
            description = "생성한 학교 시간표를 반환합니다."
    )
    @GetMapping()
    public ResponseEntity<SchoolTimetableResponse> getSchoolTimetable() {
        SchoolTimetableResponse schoolTimetableResponse = schoolTimetableService.getSchoolTimetable();
        return ResponseEntity.ok(schoolTimetableResponse);
    }

    @Operation(
            summary = "학교 시간표 생성",
            description = "학교 시간표를 생성합니다."
    )
    @PostMapping("/create")
    public ResponseEntity<String> createSchoolTimetable() {
        schoolTimetableService.createSchoolTimetable();
        return ResponseEntity.ok("시간표가 성공적으로 생성되었습니다.");
    }

    @Operation(
            summary = "시간표 비우기",
            description = "시간표 내의 모든 스케줄을 삭제합니다."
    )
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearSchoolTimetable() {
        schoolTimetableService.clearTimetable();
        return ResponseEntity.ok("시간표의 모든 스케줄이 삭제되었습니다.");
    }

    @Operation(
            summary = "스케줄 생성",
            description = "시간표에 새로운 스케줄을 추가합니다."
    )
    @PostMapping("/{timetableId}/schedule")
    public ResponseEntity<String> createSchedule(@RequestBody SchoolScheduleRequest request) {
        schoolTimetableService.createSchedule(request);
        return ResponseEntity.ok("스케줄이 성공적으로 생성되었습니다.");
    }

    @Operation(
            summary = "스케줄 삭제",
            description = "주어진 ID의 스케줄을 삭제합니다."
    )
    @DeleteMapping("/schedule/{scheduleId}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Long scheduleId) {
        schoolTimetableService.deleteSchedule(scheduleId);
        return ResponseEntity.ok("스케줄이 삭제되었습니다.");
    }

    @Operation(
            summary = "스케줄 조회",
            description = "주어진 ID의 스케줄을 조회합니다."
    )
    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<SchoolScheduleResponse> getScheduleById(@PathVariable Long scheduleId) {
        SchoolScheduleResponse response = schoolTimetableService.getScheduleById(scheduleId);
        return ResponseEntity.ok(response);
    }
}
