package yerong.wedle.meal.repository;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.meal.domain.Meal;

public interface MealRepository extends JpaRepository<Meal, Long> {
    boolean existsBySchoolCodeAndDate(String schoolCode, LocalDate mealDate);

    void deleteBySchoolCodeAndDate(String schoolCode, LocalDate date);
}
