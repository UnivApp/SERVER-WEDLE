package yerong.wedle.block.dto;

import lombok.Data;

@Data
public class BlockedMemberInfoResponse {

    private Long memberId;
    private String nickname;
    private String profileImageUrl;

    public BlockedMemberInfoResponse(Long memberId, String nickname, String profileImageUrl) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }
}
