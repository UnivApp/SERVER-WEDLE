package yerong.wedle.category.event.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.category.event.domain.Festival;
import yerong.wedle.university.domain.University;

public interface FestivalRepository extends JpaRepository<Festival, Long> {
    List<Festival> findByUniversity(University university);
}
