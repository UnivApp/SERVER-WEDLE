package yerong.wedle.block.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class BlockedMemberResponse {
    private Long blockerId;
    List<BlockedMemberInfoResponse> blockedMemberInfoResponses = new ArrayList<BlockedMemberInfoResponse>();

    public BlockedMemberResponse(Long blockerId,
                                 List<BlockedMemberInfoResponse> blockedMemberInfoResponses) {
        this.blockerId = blockerId;
        this.blockedMemberInfoResponses = blockedMemberInfoResponses;
    }
}
