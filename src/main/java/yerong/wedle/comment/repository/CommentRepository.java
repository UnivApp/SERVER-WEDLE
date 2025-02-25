package yerong.wedle.comment.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.comment.domain.Comment;
import yerong.wedle.post.domain.Post;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
}
