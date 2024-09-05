package yerong.wedle.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class AccessTokenResponse {
    private String accessToken;
    private Long accessTokenExpiresIn;
}
