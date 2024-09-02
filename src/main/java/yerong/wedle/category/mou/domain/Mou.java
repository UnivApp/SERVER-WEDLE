package yerong.wedle.category.mou.domain;

import jakarta.persistence.*;
import yerong.wedle.university.domain.University;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Mou {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "mou_id")
    private Long mouId;

    private String partnerInstitute;
    private String description;
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;
}
