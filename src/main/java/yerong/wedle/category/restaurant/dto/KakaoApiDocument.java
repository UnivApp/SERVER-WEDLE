package yerong.wedle.category.restaurant.dto;

import lombok.Data;

@Data
public class KakaoApiDocument {
    private String place_name;
    private String road_address_name;
    private String address_name;
    private String phone;
    private String category_name;
    private String place_url;
    private Double x;
    private Double y;
}
