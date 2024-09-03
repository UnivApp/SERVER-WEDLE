package yerong.wedle.university.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import yerong.wedle.common.domain.BaseTimeEntity;

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
    private Long starNum;
    private String logo;

}
