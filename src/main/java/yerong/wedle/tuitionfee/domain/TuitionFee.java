package yerong.wedle.tuitionfee.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.department.domain.DepartmentType;
import yerong.wedle.university.domain.University;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TuitionFee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tuition_fee_id")
    private Long tuitionFeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DepartmentType departmentType;

    @Column(nullable = false)
    private double feeAmount;

    public TuitionFee(University university, DepartmentType departmentType, double feeAmount) {
        this.university = university;
        this.departmentType = departmentType;
        this.feeAmount = feeAmount;
    }
}
