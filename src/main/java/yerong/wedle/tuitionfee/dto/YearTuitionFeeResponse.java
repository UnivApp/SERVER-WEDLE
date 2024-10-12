package yerong.wedle.tuitionfee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class YearTuitionFeeResponse {
    private String year;
    List<TuitionFeeResponse> tuitionFeeResponseList;
}
