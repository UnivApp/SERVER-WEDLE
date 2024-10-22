package yerong.wedle.university.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.competitionRate.dto.CompetitionRateResponse;
import yerong.wedle.department.dto.DepartmentResponse;
import yerong.wedle.employmentRate.dto.EmploymentRateResponse;
import yerong.wedle.tuitionfee.dto.TuitionFeeResponse;
import yerong.wedle.tuitionfee.dto.YearTuitionFeeResponse;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UniversityAllResponse {
    private Long universityId;
    private String fullName;
    private String location;
    private String type; //국립/사립
    private String logo;
    private String phoneNumber;
    private String website;
    private String admissionSite;
    private Long starNum;
    private YearTuitionFeeResponse tuitionFeeResponse;
    private List<DepartmentResponse> departmentResponses;
    private List<CompetitionRateResponse> competitionRateResponses;
    private List<EmploymentRateResponse> employmentRateResponses;

}
