package yerong.wedle.category.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TopRestaurantResponse {
    private String name;
    private String location;
    private String placeUrl;
    private List<String> hashtags;
    private String imageUrl;
    private String topMessage;
}
