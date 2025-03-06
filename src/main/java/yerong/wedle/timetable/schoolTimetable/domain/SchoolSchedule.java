package yerong.wedle.timetable.schoolTimetable.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SchoolSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "timetable_id", nullable = false)
    private SchoolTimetable schoolTimetable;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false)
    private int period;

    @Column(nullable = false)
    private String subject;

    private String teacher;

    @Column(nullable = false)
    private String color;

    @Builder
    public SchoolSchedule(SchoolTimetable schoolTimetable, int period, String subject, String teacher, String color,
                          DayOfWeek dayOfWeek) {
        this.schoolTimetable = schoolTimetable;
        this.period = period;
        this.subject = subject;
        this.teacher = teacher;
        this.color = color;
        this.dayOfWeek = dayOfWeek;
    }
}
