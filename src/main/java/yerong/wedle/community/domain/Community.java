package yerong.wedle.community.domain;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.board.domain.Board;
import yerong.wedle.school.domain.School;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    private List<Board> boards = new ArrayList<>();

    public Community(School school) {
        this.school = school;
    }
}
