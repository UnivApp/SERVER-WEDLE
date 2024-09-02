package yerong.wedle.star.domain;

import jakarta.persistence.*;
import yerong.wedle.common.domain.BaseTimeEntity;
import yerong.wedle.member.domain.Member;
import yerong.wedle.university.domain.University;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Star extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "star_id")
    private Long starId;

    @ManyToOne(fetch = LAZY, cascade =  ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY, cascade =  ALL)
    @JoinColumn(name = "university_id")
    private University university;
}
