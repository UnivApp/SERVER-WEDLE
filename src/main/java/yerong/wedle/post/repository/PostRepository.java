package yerong.wedle.post.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.board.domain.Board;
import yerong.wedle.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByBoard(Board board);

    List<Post> findAllByIsHot(boolean isHot);
}
