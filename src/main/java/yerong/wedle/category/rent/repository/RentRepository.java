package yerong.wedle.category.rent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.category.rent.domain.Rent;
import yerong.wedle.university.domain.University;

import java.util.List;

public interface RentRepository extends JpaRepository<Rent, Long> {
    List<Rent> findByUniversity(University university);
}
