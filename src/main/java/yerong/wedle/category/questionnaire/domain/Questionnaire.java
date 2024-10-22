package yerong.wedle.category.questionnaire.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.university.domain.University;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Questionnaire {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "questionnaire_id")
    private Long questionnaireId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionnaireCategory questionnaireCategory;

    @Column(nullable = false)
    private String question;


}
