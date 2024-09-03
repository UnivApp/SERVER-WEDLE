package yerong.wedle.category.mou.domain;

import jakarta.persistence.*;
import yerong.wedle.university.domain.University;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Mou {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "mou_id")
    private Long mouId;

    @Column(nullable = false)
    private String partnerInstitute;
    private String description;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "university_id")
    private University university;
}
