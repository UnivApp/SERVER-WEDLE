package yerong.wedle.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
public class TokenResponse {

    private String accessToken;
    private long accessTokenExpiresIn;

    private String refreshToken;
    private long refreshTokenExpiresIn;

}
