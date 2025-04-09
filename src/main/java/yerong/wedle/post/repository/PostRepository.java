package yerong.wedle.post.repository;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import yerong.wedle.board.domain.Board;
import yerong.wedle.community.domain.Community;
import yerong.wedle.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByBoard_CommunityAndIsHotBoardTrueOrderByHotBoardTimeDesc(Community community);

    List<Post> findAllByBoardOrderByCreatedAtDesc(Board board);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Post p where p.id = :id")
    Optional<Post> findByIdWithPessimisticLock(@Param("id") Long id);
}
