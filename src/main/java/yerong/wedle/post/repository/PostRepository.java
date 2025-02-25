package yerong.wedle.post.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.board.domain.Board;
import yerong.wedle.community.domain.Community;
import yerong.wedle.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByBoard_CommunityAndIsHotBoardTrueOrderByHotBoardTimeDesc(Community community);

    List<Post> findAllByBoardOrderByCreatedAtDesc(Board board);
}
