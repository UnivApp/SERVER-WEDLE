package yerong.wedle.meal.neis;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;

@Getter
public class NeisMealResponse {
    @JsonProperty("SCHUL_NM")
    private String schoolName;  // 학교 이름

    @JsonProperty("ATPT_OFCDC_SC_CODE")
    private String atptCode;

    @JsonProperty("SD_SCHUL_CODE")
    private String schoolCode;  // 학교 코드

    @JsonProperty("MLSV_YMD")
    private String date; // 문자열로 받아오기

    public LocalDate getParsedDate() {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd")); // LocalDate로 변환
    }

    @JsonProperty("CAL_INFO")
    private String calories;

    @JsonProperty("DDISH_NM")
    private String dishName; // 메뉴 이름

    @JsonProperty("NTR_INFO")
    private String nutritionInfo; // 영양 정보

    @JsonProperty("ORPLC_INFO")
    private String originInfo; // 원산지 정보

    @JsonProperty("MMEAL_SC_NM")
    private String mealType; // 급식 종류 (중식, 석식 등)

    public List<String> getDishNames() {
        String cleanedDishName = dishName.replaceAll("\\s*\\([^)]*\\)", "");
        return Arrays.asList(cleanedDishName.split("<br/>"));
    }
}
