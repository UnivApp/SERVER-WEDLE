package yerong.wedle.category.news.domain;

import jakarta.persistence.*;
import yerong.wedle.university.domain.University;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class News {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "news_id")
    private Long newsId;

    private String title;
    private LocalDateTime publishDate;
    private String link;

    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;
}
