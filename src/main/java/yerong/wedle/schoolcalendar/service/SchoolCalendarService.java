package yerong.wedle.schoolcalendar.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
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
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
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

        String redisKey = "calendar:" + school.getId() + ":" + date;

        Object cached = redisTemplate.opsForValue().get(redisKey);
        if (cached != null) {
            return objectMapper.convertValue(cached, TotalSchoolCalendarResponse.class);
        }

        List<SchoolCalendar> schoolCalendarsDB = schoolCalendarRepository.findBySchoolAndDate(school, date);
        TotalSchoolCalendarResponse response;
        if (!schoolCalendarsDB.isEmpty()) {
            response = convertToSchoolCalendarResponse(schoolCalendarsDB, school.getName());
        } else {
            List<NeisSchoolCalendarResponse> scheduleByDate = neisSchoolCalendarApiClient.getScheduleByDate(
                    school.getSchoolCode(), date, school.getAtptCode());
            response = convertToSchoolCalendarResponseByNeis(scheduleByDate, school.getName());
        }

        redisTemplate.opsForValue().set(redisKey, objectMapper.convertValue(response, Map.class));

        return response;
    }

    public TotalSchoolCalendarResponse getScheduleBetween(SchoolCalendarBetweenDatesRequest request) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);
        School school = member.getSchool();

        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();

        String redisKey = "calendar:range:" + school.getId() + ":" + startDate + ":" + endDate;

        Object cached = redisTemplate.opsForValue().get(redisKey);
        if (cached != null) {
            return objectMapper.convertValue(cached, TotalSchoolCalendarResponse.class);
        }

        List<SchoolCalendar> schoolCalendarsDB = schoolCalendarRepository.findBySchoolAndDateBetween(school, startDate,
                endDate);
        TotalSchoolCalendarResponse response;

        if (!schoolCalendarsDB.isEmpty()) {
            response = convertToSchoolCalendarResponse(schoolCalendarsDB, school.getName());
        } else {
            List<NeisSchoolCalendarResponse> scheduleByDate = neisSchoolCalendarApiClient.getScheduleBetween(
                    school.getSchoolCode(), startDate, endDate, school.getAtptCode());
            response = convertToSchoolCalendarResponseByNeis(scheduleByDate, school.getName());
        }

        redisTemplate.opsForValue().set(redisKey, objectMapper.convertValue(response, Map.class));

        return response;
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
        LocalDate startDate = now.withDayOfYear(1); // 올해 1월 1일
        LocalDate endDate = now.withDayOfYear(now.lengthOfYear()); // 올해 12월 31일

        List<NeisSchoolCalendarResponse> yearSchedules = neisSchoolCalendarApiClient.getScheduleBetween(
                school.getSchoolCode(), startDate, endDate, school.getAtptCode());

        TotalSchoolCalendarResponse yearTotalSchoolCalendarResponse = convertToSchoolCalendarResponseByNeis(
                yearSchedules, school.getName());

        saveOrUpdateSchoolCalendars(school, yearTotalSchoolCalendarResponse);
    }


    @Scheduled(cron = "0 0 0 1 1 ?") // 매년 1월 1일 00:00 실행
    public void refreshSchoolCalendars() {
        List<School> schools = schoolRepository.findAll();
        for (School school : schools) {
            LocalDate lastYearStart = LocalDate.now().minusYears(1).withDayOfYear(1);
            LocalDate lastYearEnd = lastYearStart.withDayOfYear(lastYearStart.lengthOfYear());
            List<SchoolCalendar> lastYearCalendars = schoolCalendarRepository.findBySchoolAndDateBetween(
                    school, lastYearStart, lastYearEnd);
            schoolCalendarRepository.deleteAll(lastYearCalendars);

            initializeSchoolCalendar(school);
        }
    }

    private void saveOrUpdateSchoolCalendars(School school, TotalSchoolCalendarResponse calendarResponses) {
        long startTime = System.currentTimeMillis(); // 시작 시간 측정
        List<SchoolCalendar> schoolCalendarsToSave = calendarResponses.getSchoolCalendarResponses().stream()
                .flatMap(response -> response.getEvents().stream()
                        .map(event -> SchoolCalendar.builder()
                                .school(school)
                                .content(event.getContent())
                                .date(response.getDate())
                                .oneGradeEventYN(event.isOneGradeEventYN())
                                .twoGradeEventYN(event.isTwoGradeEventYN())
                                .threeGradeEventYN(event.isThreeGradeEventYN())
                                .fourGradeEventYN(event.isFourGradeEventYN())
                                .fiveGradeEventYN(event.isFiveGradeEventYN())
                                .sixGradeEventYN(event.isSixGradeEventYN())
                                .eventName(event.getEventName())
                                .build()))
                .collect(Collectors.toList());

        schoolCalendarRepository.saveAll(schoolCalendarsToSave);

        long endTime = System.currentTimeMillis(); // 종료 시간 측정
        long elapsedTime = endTime - startTime;

        System.out.println("[SchoolCalendarService] saveOrUpdateSchoolCalendars 실행 시간: " + elapsedTime + " ms");

    }

}
