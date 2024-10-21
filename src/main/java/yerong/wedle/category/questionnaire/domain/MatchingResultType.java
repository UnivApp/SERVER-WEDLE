package yerong.wedle.category.questionnaire.domain;

import lombok.Getter;

@Getter
public enum MatchingResultType {
    PRACTICAL_APPLICATION_TYPE( "실용적 실천형(Practical Application Type)"),
    BALANCED_TYPE("균형형 (Balanced Type)"),
    ANALYTICAL_TYPE("분석형 (Analytical Type)"),
    CREATIVE_INNOVATOR_TYPE("창의적 혁신형 (Creative Innovator Type)"),
    PROFESSIONAL_RESEARCH_TYPE("전문 연구형 (Professional Research Type)");

    private final String displayName;

    MatchingResultType(String displayName) {
        this.displayName = displayName;
    }
}
