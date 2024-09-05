package yerong.wedle.category.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.category.news.domain.News;
import yerong.wedle.university.domain.University;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByUniversity(University university);
}
