package yerong.wedle.category.activity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityResponse {

    private String name;
    private String description;
    private String location;
    private List<String> imageUrls;
}
