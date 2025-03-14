package yerong.wedle.block.domain;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.common.domain.BaseTimeEntity;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class Block extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long blockerId;
    private Long blockedMemberId;

    @Builder
    public Block(Long id, Long blockerId, Long blockedMemberId) {
        this.id = id;
        this.blockerId = blockerId;
        this.blockedMemberId = blockedMemberId;
    }
}
