package yerong.wedle.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NicknameDuplicateResponse {
    private final boolean isDuplicate;
    private final String message;
}
