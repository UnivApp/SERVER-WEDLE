package yerong.wedle.category.mou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.category.mou.domain.Mou;
import yerong.wedle.university.domain.University;

import java.util.List;

public interface MouRepository extends JpaRepository<Mou, Long> {
    List<Mou> findByUniversity(University university);
}
