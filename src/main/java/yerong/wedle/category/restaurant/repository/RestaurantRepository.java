package yerong.wedle.category.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.category.restaurant.domain.Restaurant;
import yerong.wedle.university.domain.University;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByUniversity(University university);
}
