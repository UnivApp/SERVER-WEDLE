package yerong.wedle.board.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public enum BoardType {
    FREE("FREE", "자유게시판"),
    INFORMATION("INFORMATION", "정보게시판"),
    SECRET("SECRET", "비밀게시판"),
    HOT("HOT", "HOT게시판"),
    CUSTOM("CUSTOM", "사용자생성게시판");

    public String key;
    @Getter
    public String name;
}
