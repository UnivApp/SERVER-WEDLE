package yerong.wedle.category.mou.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.university.domain.University;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Mou {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "mou_id")
    private Long mouId;

    @Column(nullable = false)
    private String partnerInstitute;
    private String description;

    @Enumerated(EnumType.STRING)
    private MouCategory mouCategory;
    private String agreementDate;
    private String department;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "university_id")
    private University university;
}
