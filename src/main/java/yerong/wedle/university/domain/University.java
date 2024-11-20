package yerong.wedle.university.domain;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import yerong.wedle.category.activity.domain.ActivityUniversity;
import yerong.wedle.category.event.domain.Festival;

@Entity
@Getter
public class University {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "university_id")
    private Long universityId;

    @Column(nullable = false)
    private String name;

    private String subName;

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

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Festival> festivals = new ArrayList<>();
}
