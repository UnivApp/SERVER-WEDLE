package yerong.wedle.category.ranking.domain;

public enum RankingType {
    QS("QS", "QS 세계대학평가", "영국의 기관인 Quacquarelli Symonds(약칭 QS)에서 시행하는 대학평가."),
    THE("THE", "THE 세계대학평가", "영국런던의 고등 교육 관련 주간지 The Times Higher Education에서 발행하는 연간 고등 교육기관 평가"),
    ARWU("ARWU", "ARWU (Academic Ranking of World Universities)", "대학의 학술적 수준을 연구자 수, 논문 수 등의 지표로 대학들을 평가하는 세계 대학 랭킹"),
    CWUR("CWUR", "CWUR (Center for World University Rankings)", "2012년부터 발표하고 있는 세계 대학 평가"),
    USN_WR("USN & WR", "USN & WR (US News & World Report)", "미국 내 대학/대학원 평가에 있어서 가장 오래된 평가이며, 가장 유명한 평가"),
    CWTS("CWTS", "CWTS (Centre for Science and Technology Studies)", "네덜란드 소재 레이던 대학교의 과학기술연구센터(CWTS)에서 발표하는 세계 대학 순위"),
    NATURE_INDEX("Nature Index", "Nature Index", "과학저널인 네이처(Nature)가 2016년부터 국제 유력 학술지에 발표된 논문들을 바탕으로 발표한 연구기관 순위");

    private final String displayName;
    private final String fullName;
    private final String description;

    RankingType(String displayName, String fullName, String description) {
        this.displayName = displayName;
        this.fullName = fullName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }
}
