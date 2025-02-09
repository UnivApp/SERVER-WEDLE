package yerong.wedle.school.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.school.domain.School;

public interface SchoolRepository extends JpaRepository<School, Long> {
    List<School> findByNameContaining(String name);
}
