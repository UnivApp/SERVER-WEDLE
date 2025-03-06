package yerong.wedle.timetable.personalTimetable.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.timetable.DayOfWeek;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "personal_timetable_id", nullable = false)
    private PersonalTimetable personalTimetable;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private String color;

    @Builder
    public PersonalSchedule(PersonalTimetable personalTimetable, DayOfWeek dayOfWeek, String title, LocalTime startTime,
                            LocalTime endTime, String color) {
        this.personalTimetable = personalTimetable;
        this.dayOfWeek = dayOfWeek;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.color = color;
    }
}
