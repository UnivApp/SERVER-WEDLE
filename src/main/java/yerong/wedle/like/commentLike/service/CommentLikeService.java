package yerong.wedle.like.commentLike.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import yerong.wedle.comment.domain.Comment;
import yerong.wedle.comment.exception.CommentNotFoundException;
import yerong.wedle.comment.repository.CommentRepository;
import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;
import yerong.wedle.like.commentLike.domain.CommentLike;
import yerong.wedle.like.commentLike.exception.CommentLikeNotFoundException;
import yerong.wedle.like.commentLike.repository.CommentLikeRepository;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    public void addLike(Long commentId) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(MemberNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        boolean alreadyLiked = commentLikeRepository.existsByMemberAndComment(member, comment);
        if (!alreadyLiked) {
            CommentLike commentLike = CommentLike.builder()
                    .member(member)
                    .comment(comment)
                    .build();
            commentLikeRepository.save(commentLike);
        }
    }

    public void removeLike(Long commentId) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(MemberNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        CommentLike commentLike = commentLikeRepository.findByMemberAndComment(member, comment).orElseThrow(
                CommentLikeNotFoundException::new);

        authorizeMember(commentLike);

        commentLikeRepository.delete(commentLike);
    }

    public Long likeCount(Long commentId) {
        return commentLikeRepository.countByCommentId(commentId);
    }

    private void authorizeMember(CommentLike commentLike) {
        String socialId = getCurrentUserId();

        if (!commentLike.getMember().getSocialId().equals(socialId)) {
            throw new CustomException(ResponseCode.FORBIDDEN);
        }
    }

    private String getCurrentUserId() {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();
        return socialId;
    }
}
