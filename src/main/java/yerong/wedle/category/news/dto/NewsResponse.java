package yerong.wedle.category.news.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponse {

    private String title;
    private String source;
    private String link;
}
