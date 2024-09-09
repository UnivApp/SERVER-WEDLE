package yerong.wedle.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
@AllArgsConstructor
public class MemberLogoutResponse {
    private String socialId;
    private String refreshToken;
}
