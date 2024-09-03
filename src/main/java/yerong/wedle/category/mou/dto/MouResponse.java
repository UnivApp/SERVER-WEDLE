package yerong.wedle.category.mou.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MouResponse {

    private String partnerInstitute;
    private String description;
    private LocalDateTime date;
}
