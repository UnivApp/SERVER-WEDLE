package yerong.wedle.category.news.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponse {

    private Long newsId;
    private String title;
    private String link;
    private LocalDate publishedDate;
    private String admissionYear;
    private String source;
}
