package yerong.wedle.board.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.board.domain.Board;
import yerong.wedle.community.domain.Community;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByCommunity(Community community);

    boolean existsByTitleAndCommunity(String title, Community community);
}
