package yerong.wedle.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.category.activity.domain.Activity;
import yerong.wedle.university.domain.University;

public interface UniversityRepository extends JpaRepository<University, Long> {
}
