package yerong.wedle.school.domain;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.common.domain.BaseTimeEntity;
import yerong.wedle.community.domain.Community;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class School extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String schoolCode;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    private String phone;
    private String hompage;

    @OneToOne(mappedBy = "school", cascade = CascadeType.ALL)
    private Community community;

    @Builder
    public School(String schoolCode, String name, String address, String phone, String hompage) {
        this.schoolCode = schoolCode;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.hompage = hompage;
    }
}
