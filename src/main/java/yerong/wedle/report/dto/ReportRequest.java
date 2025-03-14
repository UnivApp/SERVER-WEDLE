package yerong.wedle.report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import yerong.wedle.report.domain.ReportTargetType;
import yerong.wedle.report.domain.ReportType;

@AllArgsConstructor
@Data
public class ReportRequest {
    private Long targetId;
    private ReportTargetType targetType;
    private ReportType reportType;
}
