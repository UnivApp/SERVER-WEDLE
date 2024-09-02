package yerong.wedle.category.rent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.category.activity.domain.Activity;
import yerong.wedle.category.rent.domain.Rent;

public interface RentRepository extends JpaRepository<Rent, Long> {
}
