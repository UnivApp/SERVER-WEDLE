package yerong.wedle.category.activity.domain;

import jakarta.persistence.*;
import yerong.wedle.university.domain.University;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "activity_id")
    private Long activityId;

    @Column(nullable = false)
    private String name;
    private String description;

    @Column(nullable = false)
    private String location;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "university_id")
    private University university;
}
