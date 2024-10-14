package yerong.wedle.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.category.activity.domain.Activity;
import yerong.wedle.university.domain.University;

import java.util.List;
import java.util.Optional;

public interface UniversityRepository extends JpaRepository<University, Long> {

    List<University> findByNameContainingOrLocationContaining(String name, String location);

    List<University> findAllByOrderByNameAsc();
}
