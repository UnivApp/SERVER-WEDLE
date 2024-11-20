package yerong.wedle.member.domain;

import jakarta.persistence.*;
import lombok.*;
import yerong.wedle.common.domain.BaseTimeEntity;
import yerong.wedle.notification.domain.Notification;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

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

    @Column(nullable = false)
    private String socialId;

    @Column(unique = true)
    private String nickname;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private boolean isExistingMember;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // Member와 Notification 간의 관계 설정
    private List<Notification> notifications;

    public void setExistingMember(boolean isExistingMember) {
        this.isExistingMember = isExistingMember;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
