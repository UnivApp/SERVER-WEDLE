package yerong.wedle.common.domain;

public enum Visibility {
    PRIVATE("나만 보기"), FRIENDS("친구 공개"), PUBLIC("전체 공개");
    private String text;

    Visibility(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
