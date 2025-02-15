package yerong.wedle.like.postLike.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import yerong.wedle.comment.repository.CommentRepository;
import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;
import yerong.wedle.like.postLike.domain.PostLike;
import yerong.wedle.like.postLike.repository.PostLikeRepository;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.repository.MemberRepository;
import yerong.wedle.post.domain.Post;
import yerong.wedle.post.exception.PostNotFoundException;
import yerong.wedle.post.repository.PostRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public void addLike(Long postId) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(MemberNotFoundException::new);
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

        boolean alreadyLiked = postLikeRepository.existsByMemberAndPost(member, post);
        if (!alreadyLiked) {
            PostLike postLike = PostLike.builder()
                    .member(member)
                    .post(post)
                    .build();
            postLikeRepository.save(postLike);
        }
    }

    public void removeLike(Long postId) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(MemberNotFoundException::new);
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

        PostLike postLike = postLikeRepository.findByMemberAndPost(member, post).orElseThrow(
                PostNotFoundException::new);

        authorizeMember(postLike);

        postLikeRepository.delete(postLike);
    }

    public Long likeCount(Long postId) {
        return postLikeRepository.countByPostId(postId);
    }

    private void authorizeMember(PostLike postLike) {
        String socialId = getCurrentUserId();

        if (!postLike.getMember().getSocialId().equals(socialId)) {
            throw new CustomException(ResponseCode.FORBIDDEN);
        }
    }

    private String getCurrentUserId() {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();
        return socialId;
    }
}
