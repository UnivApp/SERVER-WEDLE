package yerong.wedle.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class TokenResponse {

    private String accessToken;
    private Long accessTokenExpiresIn;
    private String refreshToken;
}
