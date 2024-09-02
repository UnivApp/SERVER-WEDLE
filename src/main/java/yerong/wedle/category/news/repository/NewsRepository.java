package yerong.wedle.category.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.category.activity.domain.Activity;
import yerong.wedle.category.news.domain.News;

public interface NewsRepository extends JpaRepository<News, Long> {
}
