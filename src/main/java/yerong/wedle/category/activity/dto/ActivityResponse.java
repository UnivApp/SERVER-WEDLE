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
    private String tip;
    private String location;
    private List<ActivityImageResponse> images; // 이미지 URL과 출처를 함께 반환
}
