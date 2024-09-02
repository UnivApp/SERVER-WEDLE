package yerong.wedle.category.mou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.category.activity.domain.Activity;
import yerong.wedle.category.mou.domain.Mou;

public interface MouRepository extends JpaRepository<Mou, Long> {
}
