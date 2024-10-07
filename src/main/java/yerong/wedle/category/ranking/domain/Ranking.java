package yerong.wedle.category.ranking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.university.domain.University;

import java.util.List;

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

    private int rank;

    @Column(name = "year")
    private int year;

    private String universityName;
    private String logo;
}
