package yerong.wedle.block.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.block.domain.Block;

public interface BlockRepository extends JpaRepository<Block, Long> {

    boolean existsByBlockerIdAndBlockedMemberId(Long memberId, Long memberId1);

    List<Block> findByBlockerId(Long blockerId);

    Optional<Block> findByBlockerIdAndBlockedMemberId(Long blockerId, Long blockedMemberId);
}
