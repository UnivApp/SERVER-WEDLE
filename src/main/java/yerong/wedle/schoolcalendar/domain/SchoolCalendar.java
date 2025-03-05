package yerong.wedle.schoolcalendar.domain;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.school.domain.School;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class SchoolCalendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String eventName;

    @Column(nullable = false)
    private LocalDate date;

    private String content;

    private boolean oneGradeEventYN; // 1학년 행사 여부
    private boolean twoGradeEventYN; // 2학년 행사 여부
    private boolean threeGradeEventYN; // 3학년 행사 여부
    private boolean fourGradeEventYN; // 4학년 행사 여부
    private boolean fiveGradeEventYN; // 5학년 행사 여부
    private boolean sixGradeEventYN; // 6학년 행사 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @Builder
    public SchoolCalendar(School school, String eventName, LocalDate date, String content, boolean oneGradeEventYN,
                          boolean twoGradeEventYN, boolean threeGradeEventYN, boolean fourGradeEventYN,
                          boolean fiveGradeEventYN, boolean sixGradeEventYN) {
        this.school = school;
        this.eventName = eventName;
        this.date = date;
        this.content = content;
        this.oneGradeEventYN = oneGradeEventYN;
        this.twoGradeEventYN = twoGradeEventYN;
        this.threeGradeEventYN = threeGradeEventYN;
        this.fourGradeEventYN = fourGradeEventYN;
        this.fiveGradeEventYN = fiveGradeEventYN;
        this.sixGradeEventYN = sixGradeEventYN;
    }


}
