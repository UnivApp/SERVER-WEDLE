package yerong.wedle.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yerong.wedle.report.domain.Report;
import yerong.wedle.report.domain.ReportTargetType;

public interface ReportRepository extends JpaRepository<Report, Long> {
    long countByTargetIdAndTargetType(Long targetId, ReportTargetType targetType);

    long countByTargetOwnerId(long targetOwnerId);

    void deleteByTargetIdAndTargetType(Long targetId, ReportTargetType targetType);

    @Query("SELECT COUNT(r) > 0 FROM Report r WHERE r.reportId = :reportId AND r.targetId = :targetId AND r.targetType = :targetType")
    boolean existsByReportIdAndTargetIdAndTargetType(Long reportId, Long targetId, ReportTargetType targetType);
}
