package yerong.wedle.block.dto;

import lombok.Data;

@Data
public class BlockRequest {
    private Long blockedMemberId;

    public BlockRequest() {

    }

    public BlockRequest(Long blockedMemberId) {
        this.blockedMemberId = blockedMemberId;
    }
}
