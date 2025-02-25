package yerong.wedle.school.neis;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class NeisSchoolResponse {
    @JsonProperty("SCHUL_NM")
    private String schoolName;  // 학교 이름

    @JsonProperty("SD_SCHUL_CODE")
    private String schoolCode;  // 학교 코드

    @JsonProperty("ORG_RDNMA")
    private String roadAddress; // 도로명주소

    @JsonProperty("ORG_TELNO")
    private String phoneNumber; // 전화번호

    @JsonProperty("HMPG_ADRES")
    private String website;     // 홈페이지 주소
}
