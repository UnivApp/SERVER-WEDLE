package yerong.wedle.category.restaurant.dto;

import java.util.List;
import lombok.Data;

@Data
public class KakaoApiResponse {
    private List<KakaoApiDocument> documents;
}