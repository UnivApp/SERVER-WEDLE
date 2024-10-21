package yerong.wedle.category.questionnaire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.category.questionnaire.domain.Questionnaire;
import yerong.wedle.category.questionnaire.domain.QuestionnaireCategory;
import yerong.wedle.university.domain.University;

import java.util.List;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {
    List<Questionnaire> findAllByQuestionnaireCategory(QuestionnaireCategory questionnaireCategory);
}
