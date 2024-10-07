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
    private LocalDateTime accessTokenExpiresIn;
    private String refreshToken;
    private LocalDateTime refreshTokenExpiresIn;

}
