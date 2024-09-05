package yerong.wedle.category.rent.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.university.domain.University;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class Rent {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "rent_id")
    private Long rentId;

    @Column(nullable = false)
    private String area;

    @Column(nullable = false)
    private Double averageRent;
    private String source;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "university_id")
    private University university;
}
