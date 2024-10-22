package yerong.wedle.category.questionnaire.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.category.questionnaire.domain.MatchingResult;
import yerong.wedle.category.questionnaire.domain.MatchingResultType;
import yerong.wedle.category.questionnaire.domain.Questionnaire;
import yerong.wedle.category.questionnaire.domain.QuestionnaireCategory;
import yerong.wedle.category.questionnaire.dto.MatchingResultResponse;
import yerong.wedle.category.questionnaire.dto.QuestionnaireResponse;
import yerong.wedle.category.questionnaire.repository.MatchingResultRepository;
import yerong.wedle.category.questionnaire.repository.QuestionnaireRepository;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MatchingService {

    private final QuestionnaireRepository questionnaireRepository;
    private final MatchingResultRepository matchingResultRepository;

    public List<QuestionnaireResponse> getAllByCategory() {
        return List.of(QuestionnaireCategory.values()).stream()
                .map(this::convertQuestionnaireDto)
                .collect(Collectors.toList());
    }
    private QuestionnaireResponse convertQuestionnaireDto(QuestionnaireCategory category) {
        List<Questionnaire> questionnaires = questionnaireRepository.findAllByQuestionnaireCategory(category);

        List<String> questions = questionnaires.stream()
                .map(Questionnaire::getQuestion)
                .collect(Collectors.toList());

        return new QuestionnaireResponse(category.getDisplayName(), questions);
    }

    public MatchingResultResponse getMatchingResult(int score) {
        Optional<MatchingResult> matchingResult = findResultByScore(score);
        return matchingResult.map(result ->
                        new MatchingResultResponse(
                                result.getMatchingResultId(),
                                result.getMatchingResultType().getDisplayName(),
                                result.getRecommand(),
                                result.getDescription()))
                .orElse(null);
    }

    private Optional<MatchingResult> findResultByScore(int score) {
        if (score >= 5 && score <= 30) {
            return matchingResultRepository.findByMatchingResultType(MatchingResultType.PRACTICAL_APPLICATION_TYPE);
        } else if (score >= 31 && score <= 50) {
            return matchingResultRepository.findByMatchingResultType(MatchingResultType.BALANCED_TYPE);
        } else if (score >= 51 && score <= 70) {
            return matchingResultRepository.findByMatchingResultType(MatchingResultType.ANALYTICAL_TYPE);
        } else if (score >= 71 && score <= 90) {
            return matchingResultRepository.findByMatchingResultType(MatchingResultType.CREATIVE_INNOVATOR_TYPE);
        } else if (score >= 91 && score <= 100) {
            return matchingResultRepository.findByMatchingResultType(MatchingResultType.PROFESSIONAL_RESEARCH_TYPE);
        }
        return Optional.empty();
    }
}
