package yerong.wedle.category.activity.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.university.domain.University;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ActivityUniversity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "activity_university_id")
    private Long activityUniversityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", nullable = false)
    private University university;


    public ActivityUniversity(Activity activity, University university) {
        this.activity = activity;
        this.university = university;
    }
}
