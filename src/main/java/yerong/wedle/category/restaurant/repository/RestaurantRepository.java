package yerong.wedle.category.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.category.activity.domain.Activity;
import yerong.wedle.category.restaurant.domain.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
