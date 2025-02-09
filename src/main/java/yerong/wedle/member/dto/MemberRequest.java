package yerong.wedle.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class MemberRequest {

    private String socialId;
    private String name;
    private String email;
    private Long schoolId;
}
