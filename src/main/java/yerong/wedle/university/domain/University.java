package yerong.wedle.university.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import yerong.wedle.common.domain.BaseTimeEntity;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class University {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "university_id")
    private Long universityId;

    @Column(nullable = false)
    private String name;
    private String region;
    private String logo;
}
