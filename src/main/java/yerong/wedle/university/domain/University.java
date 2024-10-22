package yerong.wedle.university.domain;

import jakarta.persistence.*;
import lombok.Getter;
import yerong.wedle.category.activity.domain.ActivityUniversity;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
public class University {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "university_id")
    private Long universityId;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String location;
    private String type; //국립/사립
    @Column(name = "phone_number")
    private String phoneNumber;
    private String website;

    @Column(name = "admission_site")
    private String admissionSite;
    private String logo;

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActivityUniversity> activities = new ArrayList<>();
}
