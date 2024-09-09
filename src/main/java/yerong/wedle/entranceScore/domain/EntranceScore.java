package yerong.wedle.entranceScore.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class EntranceScore {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "entrance_score_id")
    private Long entranceScoreId;

    @Column(nullable = false)
    private String image;
}
