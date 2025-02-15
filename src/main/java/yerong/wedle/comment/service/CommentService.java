package yerong.wedle.comment.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.comment.domain.Comment;
import yerong.wedle.comment.dto.CommentRequest;
import yerong.wedle.comment.dto.CommentResponse;
import yerong.wedle.comment.exception.CommentNotFoundException;
import yerong.wedle.comment.repository.CommentRepository;
import yerong.wedle.like.commentLike.repository.CommentLikeRepository;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.repository.MemberRepository;
import yerong.wedle.post.domain.Post;
import yerong.wedle.post.exception.PostNotFoundException;
import yerong.wedle.post.repository.PostRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final CommentLikeRepository commentLikeRepository;

    public CommentResponse createComment(CommentRequest commentRequest) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(MemberNotFoundException::new);

        Post post = postRepository.findById(commentRequest.getPostId()).orElseThrow(PostNotFoundException::new);
        Comment parentComment = null;
        Comment comment;
        if (commentRequest.getParentCommentId() != null) {
            parentComment = commentRepository.findById(commentRequest.getParentCommentId())
                    .orElseThrow(CommentNotFoundException::new);

            comment = Comment.builder()
                    .content(commentRequest.getContent())
                    .post(post)
                    .member(member)
                    .isAnonymous(commentRequest.isAnonymous())
                    .parentComment(parentComment)
                    .build();
            commentRepository.save(comment);
            parentComment.addReply(comment);
        } else {
            comment = Comment.builder()
                    .content(commentRequest.getContent())
                    .post(post)
                    .member(member)
                    .isAnonymous(commentRequest.isAnonymous())
                    .parentComment(null)
                    .build();
            commentRepository.save(comment);
        }
        return convertToCommentResponse(comment);
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        commentRepository.delete(comment);
    }

    public CommentResponse getComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        return convertToCommentResponse(comment);
    }

    public List<CommentResponse> getComments(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        List<Comment> comments = commentRepository.findAllByPost(post);
        return comments.stream().map(this::convertToCommentResponse).collect(Collectors.toList());
    }

    public Long likeCount(Long commentId) {
        return commentLikeRepository.countByCommentId(commentId);
    }

    public boolean isLiked(Long commentId) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(MemberNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        return commentLikeRepository.existsByMemberAndComment(member, comment);

    }

    private CommentResponse convertToCommentResponse(Comment comment) {
        Long count = likeCount(comment.getId());
        boolean isLiked = isLiked(comment.getId());
        return new CommentResponse(comment.getId(), comment.getContent(), comment.isAnonymous(), count, isLiked);
    }

    private String getCurrentUserId() {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();

        return socialId;
    }
}
