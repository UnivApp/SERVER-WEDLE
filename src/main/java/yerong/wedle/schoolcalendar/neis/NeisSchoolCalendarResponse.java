package yerong.wedle.schoolcalendar.neis;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class NeisSchoolCalendarResponse {
    @JsonProperty("EVENT_NM")
    private String eventName;  // 학교 이름

    @JsonProperty("AA_YMD")
    private String date; //학사 일자

    public LocalDate getParsedDate() {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd")); // LocalDate로 변환
    }

    @JsonProperty("EVENT_CNTNT")
    private String content;  // 행사 내용

    @JsonProperty("ONE_GRADE_EVENT_YN")
    private String oneGradeEventYN; // 1학년 행사 여부

    @JsonProperty("TW_GRADE_EVENT_YN")
    private String twoGradeEventYN; // 2학년 행사 여부

    @JsonProperty("THREE_GRADE_EVENT_YN")
    private String threeGradeEventYN; // 3학년 행사 여부

    @JsonProperty("FR_GRADE_EVENT_YN")
    private String fourGradeEventYN; // 4학년 행사 여부

    @JsonProperty("FIV_GRADE_EVENT_YN")
    private String fiveGradeEventYN; // 5학년 행사 여부

    @JsonProperty("SIX_GRADE_EVENT_YN")
    private String sixGradeEventYN; // 6학년 행사 여부
}
