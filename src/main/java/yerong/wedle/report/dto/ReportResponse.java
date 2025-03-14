package yerong.wedle.report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.tool.schema.TargetType;
import yerong.wedle.report.domain.ReportType;

@AllArgsConstructor
@Data
public class ReportResponse {
    private Long reporterId;
    private Long targetId;
    private TargetType targetType;
    private ReportType reportType;
}
