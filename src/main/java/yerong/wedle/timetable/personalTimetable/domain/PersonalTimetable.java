package yerong.wedle.timetable.personalTimetable.domain;

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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalTimetable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Visibility visibility = Visibility.PRIVATE;

    @OneToMany(mappedBy = "personalTimetable", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonalSchedule> schedules;

    @Builder
    public PersonalTimetable(Member member, String title, Visibility visibility) {
        this.member = member;
        this.visibility = visibility;
    }

    public void addSchedules(PersonalSchedule personalSchedule) {
        this.schedules.add(personalSchedule);
    }

    public void updateVisibility(Visibility visibility) {
        this.visibility = visibility;
    }
}
