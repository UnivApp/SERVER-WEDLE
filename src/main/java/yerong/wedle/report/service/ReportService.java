package yerong.wedle.report.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import yerong.wedle.comment.service.CommentService;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.exception.MemberNicknameDuplicateException;
import yerong.wedle.member.repository.MemberRepository;
import yerong.wedle.member.service.MemberService;
import yerong.wedle.post.service.PostService;
import yerong.wedle.report.domain.Report;
import yerong.wedle.report.domain.ReportTargetType;
import yerong.wedle.report.dto.ReportRequest;
import yerong.wedle.report.exception.AlreadyReportedCommentException;
import yerong.wedle.report.exception.AlreadyReportedPostException;
import yerong.wedle.report.repository.ReportRepository;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReportService {
    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;
    private final PostService postService;
    private final CommentService commentService;
    private final MemberService memberService;

    public void createReport(ReportRequest reportRequest) {

        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(MemberNicknameDuplicateException::new);

        boolean alreadyReported = reportRepository.existsByReportIdAndTargetIdAndTargetType(
                member.getMemberId(), reportRequest.getTargetId(), reportRequest.getTargetType());
        long targetOwnerId = 0L;
        if (reportRequest.getTargetType() == ReportTargetType.POST) {
            if (alreadyReported) {
                throw new AlreadyReportedPostException();
            }
            targetOwnerId = postService.getMemberIdByPostId(reportRequest.getTargetId());

        } else if (reportRequest.getTargetType() == ReportTargetType.COMMENT) {

            if (alreadyReported) {
                throw new AlreadyReportedCommentException();
            }
            targetOwnerId = commentService.getMemberByCommentId(reportRequest.getTargetId());
        }
        Report report = Report.builder()
                .reportId(member.getMemberId())
                .targetId(reportRequest.getTargetId())
                .targetOwnerId(targetOwnerId)
                .reportType(reportRequest.getReportType())
                .targetType(reportRequest.getTargetType())
                .build();

        reportRepository.save(report);

        long targetOwnerReportCount = reportRepository.countByTargetOwnerId(targetOwnerId);

        if (targetOwnerReportCount >= 10) {
            memberService.banMemberForDays(targetOwnerId, 30);
        }

        long reportCount = reportRepository.countByTargetIdAndTargetType(reportRequest.getTargetId(),
                reportRequest.getTargetType());
        if (reportRequest.getTargetType() == ReportTargetType.POST) {
            if (reportCount >= 5) {
                reportRepository.deleteByTargetIdAndTargetType(reportRequest.getTargetId(),
                        reportRequest.getTargetType());
                postService.deletePostByReport(reportRequest.getTargetId());
            }
        } else if (reportRequest.getTargetType() == ReportTargetType.COMMENT) {
            if (reportCount >= 5) {
                reportRepository.deleteByTargetIdAndTargetType(reportRequest.getTargetId(),
                        reportRequest.getTargetType());
                commentService.deleteCommentByReport(reportRequest.getTargetId());
            }
        }
    }

    private String getCurrentUserId() {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();

        return socialId;
    }

}
