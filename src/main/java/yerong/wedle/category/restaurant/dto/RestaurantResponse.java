package yerong.wedle.category.restaurant.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.category.restaurant.domain.Hashtag;
import yerong.wedle.university.domain.University;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantResponse {
    private String name;
    private String location;
    private String placeUrl;
    private List<String> hashtags;
    private String imageUrl;
}
