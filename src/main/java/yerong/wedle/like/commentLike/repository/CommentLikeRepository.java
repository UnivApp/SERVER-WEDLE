package yerong.wedle.like.commentLike.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.comment.domain.Comment;
import yerong.wedle.like.commentLike.domain.CommentLike;
import yerong.wedle.member.domain.Member;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByMemberAndComment(Member member, Comment comment);

    Optional<CommentLike> findByMemberAndComment(Member member, Comment comment);

    Long countByCommentId(Long commentId);
}
