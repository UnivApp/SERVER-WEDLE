package yerong.wedle.schoolcalendar.service;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.repository.MemberRepository;
import yerong.wedle.school.domain.School;
import yerong.wedle.school.repository.SchoolRepository;
import yerong.wedle.schoolcalendar.domain.SchoolCalendar;
import yerong.wedle.schoolcalendar.dto.EventDetailsResponse;
import yerong.wedle.schoolcalendar.dto.SchoolCalendarBetweenDatesRequest;
import yerong.wedle.schoolcalendar.dto.SchoolCalendarByDateRequest;
import yerong.wedle.schoolcalendar.dto.SchoolCalendarResponse;
import yerong.wedle.schoolcalendar.dto.TotalSchoolCalendarResponse;
import yerong.wedle.schoolcalendar.neis.NeisSchoolCalendarApiClient;
import yerong.wedle.schoolcalendar.neis.NeisSchoolCalendarResponse;
import yerong.wedle.schoolcalendar.repository.SchoolCalendarRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class SchoolCalendarService {
    private final SchoolCalendarRepository schoolCalendarRepository;
    private final NeisSchoolCalendarApiClient neisSchoolCalendarApiClient;
    private final MemberRepository memberRepository;
    private final SchoolRepository schoolRepository;

    public TotalSchoolCalendarResponse getScheduleByDate(SchoolCalendarByDateRequest request) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);
        School school = member.getSchool();
        LocalDate date = request.getDate();

        List<SchoolCalendar> schoolCalendarsDB = schoolCalendarRepository.findBySchoolAndDate(school, date);
        if (!schoolCalendarsDB.isEmpty()) {
            return convertToSchoolCalendarResponse(schoolCalendarsDB, school.getName());
        }

        List<NeisSchoolCalendarResponse> scheduleByDate = neisSchoolCalendarApiClient.getScheduleByDate(
                school.getSchoolCode(), request.getDate(), school.getAtptCode());
        return convertToSchoolCalendarResponseByNeis(scheduleByDate, school.getName());
    }

    public TotalSchoolCalendarResponse getScheduleBetween(SchoolCalendarBetweenDatesRequest request) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);
        School school = member.getSchool();

        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();
        List<SchoolCalendar> schoolCalendarsDB = schoolCalendarRepository.findBySchoolAndDateBetween(school, startDate,
                endDate);
        if (!schoolCalendarsDB.isEmpty()) {
            return convertToSchoolCalendarResponse(schoolCalendarsDB, school.getName());
        }

        List<NeisSchoolCalendarResponse> scheduleByDate = neisSchoolCalendarApiClient.getScheduleBetween(
                school.getSchoolCode(), request.getStartDate(), request.getEndDate(), school.getAtptCode());
        return convertToSchoolCalendarResponseByNeis(scheduleByDate, school.getName());
    }

    private TotalSchoolCalendarResponse convertToSchoolCalendarResponse(
            List<SchoolCalendar> schoolCalendars, String schoolName) {

        Map<LocalDate, List<EventDetailsResponse>> groupedEvents = schoolCalendars.stream()
                .collect(Collectors.groupingBy(
                        response -> response.getDate(),
                        Collectors.mapping(response -> new EventDetailsResponse(
                                response.getEventName(),
                                response.getContent(),
                                "Y".equals(response.isOneGradeEventYN()),
                                "Y".equals(response.isTwoGradeEventYN()),
                                "Y".equals(response.isThreeGradeEventYN()),
                                "Y".equals(response.isFourGradeEventYN()),
                                "Y".equals(response.isFiveGradeEventYN()),
                                "Y".equals(response.isSixGradeEventYN())
                        ), Collectors.toList())
                ));
        if (groupedEvents.isEmpty()) {
            return new TotalSchoolCalendarResponse(schoolName, null);
        }
        List<SchoolCalendarResponse> schoolCalendarResponses = groupedEvents.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new SchoolCalendarResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        return new TotalSchoolCalendarResponse(schoolName, schoolCalendarResponses);
    }

    private TotalSchoolCalendarResponse convertToSchoolCalendarResponseByNeis(
            List<NeisSchoolCalendarResponse> neisSchoolCalendarResponses, String schoolName) {

        Map<LocalDate, List<EventDetailsResponse>> groupedEvents = neisSchoolCalendarResponses.stream()
                .collect(Collectors.groupingBy(
                        response -> response.getParsedDate(),
                        Collectors.mapping(response -> new EventDetailsResponse(
                                response.getEventName(),
                                response.getContent(),
                                "Y".equals(response.getOneGradeEventYN()),
                                "Y".equals(response.getTwoGradeEventYN()),
                                "Y".equals(response.getThreeGradeEventYN()),
                                "Y".equals(response.getFourGradeEventYN()),
                                "Y".equals(response.getFiveGradeEventYN()),
                                "Y".equals(response.getSixGradeEventYN())
                        ), Collectors.toList())
                ));
        if (groupedEvents.isEmpty()) {
            return new TotalSchoolCalendarResponse(schoolName, null);
        }
        List<SchoolCalendarResponse> schoolCalendarResponses = groupedEvents.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new SchoolCalendarResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        return new TotalSchoolCalendarResponse(schoolName, schoolCalendarResponses);
    }

    private String getCurrentUserId() {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();

        return socialId;
    }

    public void initializeSchoolCalendar(School school) {
        LocalDate now = LocalDate.now();
        LocalDate currentMonth = now.withDayOfMonth(1);
        LocalDate previousMonth = currentMonth.minusMonths(1);
        LocalDate nextMonth = currentMonth.plusMonths(1);

        saveCurrentAndNextMonthSchoolCalendar(school, previousMonth, currentMonth, nextMonth);
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void manageSchoolCalendars() {
        List<School> schools = schoolRepository.findAll();
        for (School school : schools) {
            manageCalendars(school);
        }
    }

    private void saveCurrentAndNextMonthSchoolCalendar(School school, LocalDate previousMonth, LocalDate currentMonth,
                                                       LocalDate nextMonth) {

        List<NeisSchoolCalendarResponse> previousMonthSchedules = neisSchoolCalendarApiClient.getScheduleBetween(
                school.getSchoolCode(), previousMonth, previousMonth.plusMonths(1).minusDays(1), school.getAtptCode());

        TotalSchoolCalendarResponse previousTotalSchoolCalendarResponse = convertToSchoolCalendarResponseByNeis(
                previousMonthSchedules, school.getName());
        saveOrUpdateSchoolCalendars(school, previousTotalSchoolCalendarResponse);

        List<NeisSchoolCalendarResponse> currentMonthSchedules = neisSchoolCalendarApiClient.getScheduleBetween(
                school.getSchoolCode(), currentMonth, currentMonth.plusMonths(1).minusDays(1), school.getAtptCode());

        TotalSchoolCalendarResponse currentTotalSchoolCalendarResponse = convertToSchoolCalendarResponseByNeis(
                currentMonthSchedules, school.getName());
        saveOrUpdateSchoolCalendars(school, currentTotalSchoolCalendarResponse);

        List<NeisSchoolCalendarResponse> nextMonthSchedules = neisSchoolCalendarApiClient.getScheduleBetween(
                school.getSchoolCode(), nextMonth, nextMonth.plusMonths(1).minusDays(1), school.getAtptCode());
        TotalSchoolCalendarResponse nextTotalSchoolCalendarResponse = convertToSchoolCalendarResponseByNeis(
                nextMonthSchedules, school.getName());
        saveOrUpdateSchoolCalendars(school, nextTotalSchoolCalendarResponse);
    }

    private void manageCalendars(School school) {
        LocalDate now = LocalDate.now();
        LocalDate previousMonth = now.minusMonths(2);
        LocalDate currentMonth = now.withDayOfMonth(1);
        LocalDate nextMonth = currentMonth.plusMonths(1);

        deletePreviousMonthCalendars(previousMonth, school);

        List<NeisSchoolCalendarResponse> nextMonthSchedules = neisSchoolCalendarApiClient.getScheduleBetween(
                school.getSchoolCode(), nextMonth, nextMonth.plusMonths(1).minusDays(1), school.getAtptCode());
        TotalSchoolCalendarResponse nextTotalSchoolCalendarResponse = convertToSchoolCalendarResponseByNeis(
                nextMonthSchedules,
                school.getName());
        saveOrUpdateSchoolCalendars(school, nextTotalSchoolCalendarResponse);
    }

    private void deletePreviousMonthCalendars(LocalDate previousMonth, School school) {
        List<SchoolCalendar> previousMonthCalendars = schoolCalendarRepository.findBySchoolAndDateBetween(school,
                previousMonth.withDayOfMonth(1), previousMonth.withDayOfMonth(previousMonth.lengthOfMonth()));

        schoolCalendarRepository.deleteAll(previousMonthCalendars);
    }

    private void saveOrUpdateSchoolCalendars(School school, TotalSchoolCalendarResponse calendarResponses) {

        for (SchoolCalendarResponse response : calendarResponses.getSchoolCalendarResponses()) {
            for (EventDetailsResponse event : response.getEvents()) {
                SchoolCalendar schoolCalendar = SchoolCalendar.builder()
                        .school(school)
                        .content(event.getContent())
                        .date(response.getDate())
                        .oneGradeEventYN(event.isOneGradeEventYN())
                        .twoGradeEventYN(event.isTwoGradeEventYN())
                        .threeGradeEventYN(event.isThreeGradeEventYN())
                        .fourGradeEventYN(event.isFourGradeEventYN())
                        .fiveGradeEventYN(event.isFiveGradeEventYN())
                        .sixGradeEventYN(event.isSixGradeEventYN())
                        .eventName(event.getEventName()).build();
                schoolCalendarRepository.save(schoolCalendar);
            }
        }
    }

}
