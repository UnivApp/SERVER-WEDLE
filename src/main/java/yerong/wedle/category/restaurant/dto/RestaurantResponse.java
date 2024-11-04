package yerong.wedle.category.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantResponse {
    private String name;
    private String roadAddressName;
    private String addressName;
    private String phone;
    private String categoryName;
    private String placeUrl;
}
