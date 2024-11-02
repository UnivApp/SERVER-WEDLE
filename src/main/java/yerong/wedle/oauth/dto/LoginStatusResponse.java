package yerong.wedle.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginStatusResponse {
    private final boolean isLoggedIn;
    private final boolean isNicknameSet;
    private final String message;
}
