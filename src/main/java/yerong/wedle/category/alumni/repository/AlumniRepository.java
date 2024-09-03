package yerong.wedle.category.alumni.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.category.alumni.domain.Alumni;
import yerong.wedle.university.domain.University;

import java.util.List;

public interface AlumniRepository extends JpaRepository<Alumni, Long> {
    List<Alumni> findByUniversity(University university);
}
