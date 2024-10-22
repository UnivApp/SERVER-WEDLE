package yerong.wedle.category.news.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.university.domain.University;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class News {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "news_id")
    private Long newsId;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private LocalDate publishedDate;
    private String admissionYear;
    private String source;
}
