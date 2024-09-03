package yerong.wedle.category.alumni.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.university.domain.University;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Alumni {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "alumni_id")
    private Long alumniId;

    @Column(nullable = false)
    private String name;
    private String achievements;
    private String description;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "university_id")
    private University university;
}
