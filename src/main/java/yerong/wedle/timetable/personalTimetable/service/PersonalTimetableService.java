package yerong.wedle.timetable.personalTimetable.service;

import jakarta.transaction.Transactional;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import yerong.wedle.common.domain.Visibility;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.exception.UnauthorizedAccessException;
import yerong.wedle.member.repository.MemberRepository;
import yerong.wedle.timetable.DayOfWeek;
import yerong.wedle.timetable.personalTimetable.domain.PersonalSchedule;
import yerong.wedle.timetable.personalTimetable.domain.PersonalTimetable;
import yerong.wedle.timetable.personalTimetable.dto.PersonalScheduleRequest;
import yerong.wedle.timetable.personalTimetable.dto.PersonalScheduleResponse;
import yerong.wedle.timetable.personalTimetable.dto.PersonalTimetableResponse;
import yerong.wedle.timetable.personalTimetable.exception.PersonalScheduleAlreadyExistsException;
import yerong.wedle.timetable.personalTimetable.exception.PersonalScheduleNotFoundException;
import yerong.wedle.timetable.personalTimetable.exception.PersonalTimetableNotFoundException;
import yerong.wedle.timetable.personalTimetable.repository.PersonalScheduleRepository;
import yerong.wedle.timetable.personalTimetable.repository.PersonalTimetableRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonalTimetableService {
    private final PersonalTimetableRepository personalTimetableRepository;
    private final PersonalScheduleRepository personalScheduleRepository;
    private final MemberRepository memberRepository;

    public PersonalTimetableResponse getPersonalTimetable() {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);

        PersonalTimetable personalTimetable = personalTimetableRepository.findByMember(member)
                .orElseThrow(
                        PersonalTimetableNotFoundException::new);
        return convertToPersonalTimetableResponse(personalTimetable);
    }

    public void createPersonalTimetable() {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);
        PersonalTimetable personalTimetable = PersonalTimetable.builder()
                .member(member)
                .visibility(Visibility.PRIVATE)
                .build();
        personalTimetableRepository.save(personalTimetable);
    }

    public void clearTimetable() {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);

        PersonalTimetable personalTimetable = personalTimetableRepository.findByMember(member)
                .orElseThrow(
                        PersonalTimetableNotFoundException::new);
        if (!personalTimetable.getMember().getSocialId().equals(socialId)) {
            throw new UnauthorizedAccessException();
        }
        personalScheduleRepository.deleteInBatch(personalTimetable.getSchedules());
    }

    public void createSchedule(PersonalScheduleRequest personalScheduleRequest) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);

        PersonalTimetable personalTimetable = personalTimetableRepository.findByMember(member)
                .orElseThrow(
                        PersonalTimetableNotFoundException::new);
        DayOfWeek dayOfWeek = getDayOfWeekFromRequest(personalScheduleRequest.getDay());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = LocalTime.parse(personalScheduleRequest.getStartTime(), formatter);
        LocalTime endTime = LocalTime.parse(personalScheduleRequest.getEndTime(), formatter);

        boolean scheduleExists = personalScheduleRepository.existsByPersonalTimetableAndDayOfWeekAndStartTimeBeforeAndEndTimeAfter(
                personalTimetable, dayOfWeek, endTime, startTime);

        if (scheduleExists) {
            throw new PersonalScheduleAlreadyExistsException();
        }

        PersonalSchedule personalSchedule = PersonalSchedule.builder()
                .personalTimetable(personalTimetable)
                .dayOfWeek(dayOfWeek)
                .title(personalScheduleRequest.getTitle())
                .startTime(startTime)
                .endTime(endTime)
                .color(personalScheduleRequest.getColor())
                .build();
        personalScheduleRepository.save(personalSchedule);
        personalTimetable.addSchedules(personalSchedule);
    }

    public void deleteSchedule(Long scheduleId) {
        String socialId = getCurrentUserId();
        PersonalSchedule personalSchedule = personalScheduleRepository.findById(scheduleId)
                .orElseThrow(PersonalScheduleNotFoundException::new);
        if (!personalSchedule.getPersonalTimetable().getMember().getSocialId().equals(socialId)) {
            throw new UnauthorizedAccessException();
        }
        personalScheduleRepository.delete(personalSchedule);
    }

    public PersonalScheduleResponse getScheduleById(Long scheduleId) {
        PersonalSchedule personalSchedule = personalScheduleRepository.findById(scheduleId)
                .orElseThrow(PersonalScheduleNotFoundException::new);

        return convertToPersonalScheduleResponse(personalSchedule);
    }

    private PersonalTimetableResponse convertToPersonalTimetableResponse(PersonalTimetable personalTimetable) {
        List<PersonalScheduleResponse> personalScheduleResponses = personalTimetable.getSchedules().stream()
                .map(schedule -> new PersonalScheduleResponse(
                        schedule.getId(),
                        schedule.getDayOfWeek().getDay(),
                        schedule.getStartTime(),
                        schedule.getEndTime(),
                        schedule.getTitle(),
                        schedule.getColor()
                )).collect(Collectors.toList());
        return new PersonalTimetableResponse(personalTimetable.getId(), personalScheduleResponses);
    }

    private PersonalScheduleResponse convertToPersonalScheduleResponse(PersonalSchedule personalSchedule) {
        return new PersonalScheduleResponse(personalSchedule.getId(), personalSchedule.getDayOfWeek().getDay(),
                personalSchedule.getStartTime(), personalSchedule.getEndTime(), personalSchedule.getTitle(),
                personalSchedule.getColor());
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
