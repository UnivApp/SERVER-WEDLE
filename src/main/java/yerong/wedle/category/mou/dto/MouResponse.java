package yerong.wedle.category.mou.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.category.mou.domain.MouCategory;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MouResponse {


    private String partnerInstitute;
    private String description;
    private String mouCategory;
    private String agreementDate;
    private String department;
}
