package yerong.wedle.like.postLike.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.like.postLike.domain.PostLike;
import yerong.wedle.member.domain.Member;
import yerong.wedle.post.domain.Post;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByMemberAndPost(Member member, Post post);

    Optional<PostLike> findByMemberAndPost(Member member, Post post);

    Long countByPostId(Long postId);
}
