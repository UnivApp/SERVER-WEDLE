package yerong.wedle.like.postLike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.like.postLike.domain.PostLike;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
}
