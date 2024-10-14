package yerong.wedle.category.expo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.category.expo.domain.Expo;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExpoResponse {
    private Long expoId;
    private String title;
    private String category;
    private String expoYear;
    private String status;
    private String link;
    private String location;
    private String content;
    private String date;
}
