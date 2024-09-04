package yerong.wedle.member.domain;

import jakarta.persistence.*;
import lombok.*;
import yerong.wedle.common.domain.BaseTimeEntity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Builder
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(nullable = false)
    private String username;

    private String email;
    private String socialId;

    @Enumerated(value = EnumType.STRING)
    private Role role;
}
