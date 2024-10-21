package yerong.wedle.category.questionnaire.domain;

import lombok.Getter;

@Getter
public enum QuestionnaireCategory {
    ACADEMIC_INTEREST("학문적 관심 (Academic Interest)"),
    CAREER_GOALS("직업적 목표 (Career Goals)"),
    LEARNING_STYLE("학습 스타일 (Learning Style)"),
    SOCIAL_ORIENTATION("사회적 성향 (Social Orientation)"),
    CREATIVE_ORIENTATION("창의적 성향 (Creative Orientation)");

    private final String displayName;

    QuestionnaireCategory(String displayName) {
        this.displayName = displayName;
    }
}
