package yerong.wedle.category.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import yerong.wedle.category.restaurant.domain.MenuCategory;

@AllArgsConstructor
@Getter
public class MenuItemResponse {
    private String name;
    private String price;
    private String description;
    private String menuCategory;
}
