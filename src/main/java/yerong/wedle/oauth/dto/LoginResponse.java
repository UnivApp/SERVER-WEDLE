package yerong.wedle.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class LoginResponse {

    private String accessToken;
    private String refreshToken;
    private boolean isExistingMember;
}
