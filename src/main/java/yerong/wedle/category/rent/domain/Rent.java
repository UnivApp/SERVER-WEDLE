package yerong.wedle.category.rent.domain;

import jakarta.persistence.*;
import yerong.wedle.university.domain.University;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Rent {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "rent_id")
    private Long rentId;

    private String area;
    private Double averageRent;
    private String source;

    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;
}
