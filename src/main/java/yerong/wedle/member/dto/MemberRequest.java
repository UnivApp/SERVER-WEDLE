package yerong.wedle.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class MemberRequest {
    private String socialId;
    private String username;
    private String email;
}
