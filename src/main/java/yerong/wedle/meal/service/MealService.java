package yerong.wedle.meal.service;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import yerong.wedle.meal.domain.Meal;
import yerong.wedle.meal.domain.MealMenu;
import yerong.wedle.meal.domain.MealType;
import yerong.wedle.meal.dto.MealDateResponse;
import yerong.wedle.meal.dto.MealRequest;
import yerong.wedle.meal.dto.MealResponse;
import yerong.wedle.meal.neis.NeisMealApiClient;
import yerong.wedle.meal.neis.NeisMealResponse;
import yerong.wedle.meal.repository.MealRepository;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.repository.MemberRepository;
import yerong.wedle.school.domain.School;
import yerong.wedle.school.repository.SchoolRepository;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MealService {
    private final MealRepository mealRepository;
    private final MemberRepository memberRepository;
    private final NeisMealApiClient neisMealApiClient;
    private final SchoolRepository schoolRepository;


    public List<MealResponse> getMealsByDate(MealRequest mealRequest) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);
        School school = member.getSchool();
        List<NeisMealResponse> mealByDate = neisMealApiClient.getMealByDate(school.getSchoolCode(),
                mealRequest.getDate(),
                school.getAtptCode());

        return convertToMealResponse(mealByDate);
    }

    public List<MealDateResponse> getUpcomingMealDates() {
        LocalDate startDate = LocalDate.now();

        return Stream.iterate(startDate, date -> date.plusDays(1))
                .limit(14)
                .map(date -> new MealDateResponse(
                        date.toString(),
                        date.format(DateTimeFormatter.ofPattern("M월 d일 EEEE")) // "M월 d일 EEEE" 형식
                ))
                .collect(Collectors.toList());
    }

    public void initializeMealsForNewSchool(School school) {
        LocalDate startDate = LocalDate.now();
//        LocalDate startDate = LocalDate.of(2024, 3, 20);
        List<Meal> mealsToSave = new ArrayList<>();

        for (int i = 0; i < 14; i++) {
            LocalDate mealDate = startDate.plusDays(i);
            List<NeisMealResponse> meals = neisMealApiClient.getMealByDate(school.getSchoolCode(), mealDate,
                    school.getAtptCode());
            if (meals == null || meals.isEmpty()) {
                log.info("No meals found for date: " + mealDate);
                continue;
            }
            for (NeisMealResponse mealResponse : meals) {
                if (!mealRepository.existsBySchoolCodeAndDate(school.getSchoolCode(), mealDate)) {
                    MealType type = null;
                    if (mealResponse.getMealType().equals("중식")) {
                        type = MealType.LUNCH;
                    } else if (mealResponse.getMealType().equals("석식")) {
                        type = MealType.DINNER;
                    }
                    Meal meal = Meal.builder()
                            .school(school)
                            .schoolCode(school.getSchoolCode())
                            .date(mealDate)
                            .mealType(type)
                            .calories(mealResponse.getCalories())
                            .build();

                    for (String menuName : mealResponse.getDishNames()) {
                        MealMenu menu = new MealMenu(menuName);
                        meal.addMenu(menu);
                    }

                    mealsToSave.add(meal);
                }
            }
        }

        if (!mealsToSave.isEmpty()) {
            mealRepository.saveAll(mealsToSave);
        }
    }

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    public void updateMealsDaily() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        List<School> schools = schoolRepository.findAll();
        for (School school : schools) {
            mealRepository.deleteBySchoolCodeAndDate(school.getSchoolCode(), yesterday);

            // 오늘부터 14일 후의 급식 데이터 추가
            for (int i = 0; i < 14; i++) {
                LocalDate mealDate = today.plusDays(i);
                List<NeisMealResponse> meals = neisMealApiClient.getMealByDate(school.getSchoolCode(), mealDate,
                        school.getAtptCode());

                for (NeisMealResponse mealResponse : meals) {
                    if (!mealRepository.existsBySchoolCodeAndDate(school.getSchoolCode(), mealDate)) {
                        MealType type = null;
                        if (mealResponse.getMealType().equals("중식")) {
                            type = MealType.LUNCH;
                        } else if (mealResponse.getMealType().equals("석식")) {
                            type = MealType.DINNER;
                        }

                        Meal meal = Meal.builder()
                                .school(school)
                                .schoolCode(school.getSchoolCode())
                                .date(mealDate)
                                .mealType(type)
                                .calories(mealResponse.getCalories())
                                .build();

                        for (String menuName : mealResponse.getDishNames()) {
                            MealMenu menu = new MealMenu(menuName);
                            meal.addMenu(menu);
                        }

                        mealRepository.save(meal);
                    }
                }
            }
        }
    }

    private List<MealResponse> convertToMealResponse(List<NeisMealResponse> mealByDate) {
        return mealByDate.stream()
                .map(neisMealResponse -> new MealResponse(
                        neisMealResponse.getSchoolName(),
                        neisMealResponse.getParsedDate(),
                        neisMealResponse.getCalories(),
                        neisMealResponse.getMealType(),
                        getDishNames(neisMealResponse.getDishName())
                ))
                .collect(Collectors.toList());
    }

    private List<String> getDishNames(String dishName) {
        String cleanedDishName = dishName.replaceAll("\\s*\\([^)]*\\)", "");
        return Arrays.asList(cleanedDishName.split("<br/>"));
    }

    private String getCurrentUserId() {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();

        return socialId;
    }
}
