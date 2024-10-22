package yerong.wedle.category.questionnaire.domain;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class MatchingResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matching_result_id")
    private Long matchingResultId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MatchingResultType matchingResultType;

    @Column(nullable = false)
    private String recommand;

    @Column(columnDefinition = "TEXT")
    private String description;
}
