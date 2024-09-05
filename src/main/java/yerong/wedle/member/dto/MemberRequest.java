package yerong.wedle.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class MemberRequest {

    private String socialId;
    private String name;
    private String email;
}
