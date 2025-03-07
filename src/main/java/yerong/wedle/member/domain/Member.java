package yerong.wedle.member.domain;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.common.domain.BaseTimeEntity;
import yerong.wedle.school.domain.School;
import yerong.wedle.todo.domain.TodoList;

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

    private String profileImageUrl;
    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    @Column(name = "school_registered_date")
    private LocalDate schoolRegisteredDate;

    private int grade;
    private String className;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private boolean isExistingMember;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TodoList> todoLists;

    public void setExistingMember(boolean isExistingMember) {
        this.isExistingMember = isExistingMember;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public void setSchoolRegisteredDate(LocalDate now) {
        this.schoolRegisteredDate = now;
    }

    public void setGradeAndClass(int grade, String className) {
        this.grade = grade;
        this.className = className;
    }

    public void setProfileImageUrl(String imageUrl) {
        this.profileImageUrl = imageUrl;
    }
}
