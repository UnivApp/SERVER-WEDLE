package yerong.wedle.category.alumni.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.category.activity.domain.Activity;
import yerong.wedle.category.alumni.domain.Alumni;

public interface AlumniRepository extends JpaRepository<Alumni, Long> {
}
