package yerong.wedle.category.ranking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.university.domain.University;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Ranking {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ranking_id")
    private Long rankingId;

    @Enumerated(EnumType.STRING)
    private RankingType rankingType;

    private Integer rank;
    private String reputation;
    private String source;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "university_id")
    private University university;
}
