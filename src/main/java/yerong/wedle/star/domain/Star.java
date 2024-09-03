package yerong.wedle.star.domain;

import jakarta.persistence.*;
import lombok.*;
import yerong.wedle.common.domain.BaseTimeEntity;
import yerong.wedle.member.domain.Member;
import yerong.wedle.university.domain.University;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class Star extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "star_id")
    private Long starId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "university_id")
    private University university;

    @Builder
    public Star(Member member, University university) {
        this.member = member;
        this.university = university;
    }
}
