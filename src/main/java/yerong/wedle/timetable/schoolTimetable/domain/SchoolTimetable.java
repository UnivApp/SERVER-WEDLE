package yerong.wedle.timetable.schoolTimetable.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.common.domain.Visibility;
import yerong.wedle.member.domain.Member;
import yerong.wedle.school.domain.School;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SchoolTimetable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Visibility visibility = Visibility.PRIVATE;

    @OneToMany(mappedBy = "schoolTimetable", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SchoolSchedule> schedules;

    @Builder
    public SchoolTimetable(Member member, School school, String title, Visibility visibility) {
        this.member = member;
        this.school = school;
        this.title = title;
        this.visibility = visibility;
    }

    public void addSchedules(SchoolSchedule schoolSchedule) {
        this.schedules.add(schoolSchedule);
    }

    public void updateVisibility(Visibility visibility) {
        this.visibility = visibility;
    }
}
