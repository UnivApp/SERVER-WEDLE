package yerong.wedle.category.rent.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RentResponse {
    private String area;
    private Double averageRent;
    private String source;

}
