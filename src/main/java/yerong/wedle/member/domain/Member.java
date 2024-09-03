package yerong.wedle.member.domain;

import jakarta.persistence.*;
import yerong.wedle.common.domain.BaseTimeEntity;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(nullable = false)
    private String username;

    private String email;
    private String socialId;

}
