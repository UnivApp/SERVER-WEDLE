package yerong.wedle.report.domain;


import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.common.domain.BaseTimeEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "reports")
@Entity
public class Report extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "report_id")
    private Long reportId;

    @Column(name = "target_id")
    private Long targetId;

    @Column(name = "target_owner_id")
    private Long targetOwnerId;

    @Column(name = "target_type")
    @Enumerated(STRING)
    private ReportTargetType targetType;

    @Enumerated(STRING)
    private ReportType reportType;

    @Builder
    public Report(Long reportId, Long targetId, Long targetOwnerId, ReportTargetType targetType,
                  ReportType reportType) {
        this.reportId = reportId;
        this.targetId = targetId;
        this.targetOwnerId = targetOwnerId;
        this.targetType = targetType;
        this.reportType = reportType;
    }

}
