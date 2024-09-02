package yerong.wedle.category.activity.domain;

import jakarta.persistence.*;
import yerong.wedle.university.domain.University;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "activity_id")
    private Long activityId;

    private String name;
    private String description;
    private String location;

    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;
}
