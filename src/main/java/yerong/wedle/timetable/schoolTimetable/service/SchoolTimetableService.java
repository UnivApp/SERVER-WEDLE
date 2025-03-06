package yerong.wedle.timetable.schoolTimetable.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.exception.UnauthorizedAccessException;
import yerong.wedle.member.repository.MemberRepository;
import yerong.wedle.timetable.schoolTimetable.domain.DayOfWeek;
import yerong.wedle.timetable.schoolTimetable.domain.SchoolSchedule;
import yerong.wedle.timetable.schoolTimetable.domain.SchoolTimetable;
import yerong.wedle.timetable.schoolTimetable.dto.SchoolScheduleRequest;
import yerong.wedle.timetable.schoolTimetable.dto.SchoolScheduleResponse;
import yerong.wedle.timetable.schoolTimetable.dto.SchoolTimetableResponse;
import yerong.wedle.timetable.schoolTimetable.exception.SchoolScheduleNotFoundException;
import yerong.wedle.timetable.schoolTimetable.exception.SchoolTimetableNotFoundException;
import yerong.wedle.timetable.schoolTimetable.repository.SchoolScheduleRepository;
import yerong.wedle.timetable.schoolTimetable.repository.SchoolTimetableRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class SchoolTimetableService {
    private final SchoolTimetableRepository schoolTimetableRepository;
    private final SchoolScheduleRepository schoolScheduleRepository;
    private final MemberRepository memberRepository;

    public SchoolTimetableResponse getSchoolTimetable() {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);

        SchoolTimetable schoolTimetable = schoolTimetableRepository.findByMember(member)
                .orElseThrow(
                        SchoolTimetableNotFoundException::new);
        return convertToSchoolTimetableResponse(schoolTimetable);
    }

    public void createSchoolTimetable() {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);
        String title = member.getGrade() + "학년 " + member.getClassName() + "반 시간표";
        SchoolTimetable schoolTimetable = SchoolTimetable.builder()
                .member(member)
                .school(member.getSchool())
                .title(title)
                .build();
        schoolTimetableRepository.save(schoolTimetable);
    }

    public void clearTimetable() {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);

        SchoolTimetable schoolTimetable = schoolTimetableRepository.findByMember(member)
                .orElseThrow(
                        SchoolTimetableNotFoundException::new);
        schoolScheduleRepository.deleteInBatch(schoolTimetable.getSchedules());
    }

    public void createSchedule(SchoolScheduleRequest schoolScheduleRequest) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);

        SchoolTimetable schoolTimetable = schoolTimetableRepository.findByMember(member)
                .orElseThrow(
                        SchoolTimetableNotFoundException::new);
        DayOfWeek dayOfWeek = getDayOfWeekFromRequest(schoolScheduleRequest.getDay());
        SchoolSchedule schoolSchedule = SchoolSchedule.builder()
                .schoolTimetable(schoolTimetable)
                .dayOfWeek(dayOfWeek)
                .period(schoolScheduleRequest.getPeriod())
                .subject(schoolScheduleRequest.getSubject())
                .teacher(schoolScheduleRequest.getTeacher())
                .color(schoolScheduleRequest.getColor())
                .build();
        schoolScheduleRepository.save(schoolSchedule);
        schoolTimetable.addSchedules(schoolSchedule);
    }

    public void deleteSchedule(Long scheduleId) {
        String socialId = getCurrentUserId();
        SchoolSchedule schoolSchedule = schoolScheduleRepository.findById(scheduleId)
                .orElseThrow(SchoolScheduleNotFoundException::new);
        if (!schoolSchedule.getSchoolTimetable().getMember().getSocialId().equals(socialId)) {
            throw new UnauthorizedAccessException();
        }
        schoolScheduleRepository.delete(schoolSchedule);
    }

    public SchoolScheduleResponse getScheduleById(Long scheduleId) {
        SchoolSchedule schoolSchedule = schoolScheduleRepository.findById(scheduleId)
                .orElseThrow(SchoolScheduleNotFoundException::new);

        return convertToSchoolScheduleResponse(schoolSchedule);
    }

    private SchoolTimetableResponse convertToSchoolTimetableResponse(SchoolTimetable schoolTimetable) {
        List<SchoolScheduleResponse> schoolScheduleResponses = schoolTimetable.getSchedules().stream()
                .map(schedule -> new SchoolScheduleResponse(
                        schedule.getId(),
                        schedule.getDayOfWeek().getDay(),
                        schedule.getPeriod(),
                        schedule.getSubject(),
                        schedule.getTeacher(),
                        schedule.getColor()
                )).collect(Collectors.toList());
        return new SchoolTimetableResponse(schoolTimetable.getId(), schoolTimetable.getTitle(),
                schoolScheduleResponses);
    }

    private SchoolScheduleResponse convertToSchoolScheduleResponse(SchoolSchedule schoolSchedule) {
        return new SchoolScheduleResponse(schoolSchedule.getId(), schoolSchedule.getDayOfWeek().getDay(),
                schoolSchedule.getPeriod(),
                schoolSchedule.getSubject(), schoolSchedule.getTeacher(), schoolSchedule.getColor());
    }

    private DayOfWeek getDayOfWeekFromRequest(String dayOfWeekRequest) {
        switch (dayOfWeekRequest) {
            case "월요일":
                return DayOfWeek.MONDAY;
            case "화요일":
                return DayOfWeek.TUESDAY;
            case "수요일":
                return DayOfWeek.WEDNESDAY;
            case "목요일":
                return DayOfWeek.THURSDAY;
            case "금요일":
                return DayOfWeek.FRIDAY;
            case "토요일":
                return DayOfWeek.SATURDAY;
            case "일요일":
                return DayOfWeek.SUNDAY;
            default:
                throw new IllegalArgumentException("Invalid day of week: " + dayOfWeekRequest);
        }
    }

    private String getCurrentUserId() {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();

        return socialId;
    }
}
