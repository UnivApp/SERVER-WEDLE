package yerong.wedle.banner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BannerResponse {

    private String imageUrl;
    private String linkUrl;
    private String altText;
}
