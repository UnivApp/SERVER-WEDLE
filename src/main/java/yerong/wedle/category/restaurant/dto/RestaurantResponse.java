package yerong.wedle.category.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantResponse {
    private String name;
    private String description;
    private String location;
    private String url;
    private List<MenuItemResponse> menuItemResponses;
    private List<RestaurantPhotoResponse> photoResponses;
}
