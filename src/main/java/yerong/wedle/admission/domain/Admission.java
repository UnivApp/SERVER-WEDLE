package yerong.wedle.admission.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.university.domain.University;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Admission {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "admission_id")
    private Long admissionId;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AdmissionType admissionType;

    @Column(nullable = false)
    private double competitionRate;

    @Column(nullable = false)
    private double employmentRate;

    @Builder
    public Admission(University university, AdmissionType admissionType, double competitionRate, double employmentRate) {
        this.university = university;
        this.admissionType = admissionType;
        this.competitionRate = competitionRate;
        this.employmentRate = employmentRate;
    }
}
