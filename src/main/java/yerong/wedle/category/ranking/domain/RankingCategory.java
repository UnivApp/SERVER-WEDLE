package yerong.wedle.category.ranking.domain;


import lombok.Getter;

@Getter
public enum RankingCategory {
    P("P"),        // 일반 순위
    P_TOP_10("P(top 10%)"), // 상위 10% 순위
    PP_TOP_10("PP(top 10%)"); // 추가 상위 10% 순위

    private final String value;

    RankingCategory(String value){
        this.value = value;
    }
}
