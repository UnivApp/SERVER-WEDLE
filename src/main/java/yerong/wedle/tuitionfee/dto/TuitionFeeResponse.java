package yerong.wedle.tuitionfee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.department.domain.DepartmentType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TuitionFeeResponse {

    private String departmentType;
    private double feeAmount;
}
