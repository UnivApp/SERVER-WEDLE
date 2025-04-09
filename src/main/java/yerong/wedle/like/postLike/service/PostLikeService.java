package yerong.wedle.like.postLike.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import yerong.wedle.board.domain.Board;
import yerong.wedle.board.domain.BoardType;
import yerong.wedle.board.repository.BoardRepository;
import yerong.wedle.common.exception.CustomException;
import yerong.wedle.common.exception.ResponseCode;
import yerong.wedle.community.domain.Community;
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
    private static final int HOT_BOARD_THRESHOLD = 3;

    private final PostLikeRepository postLikeRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;

    public void addLike(Long postId) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(MemberNotFoundException::new);
        Post post = postRepository.findByIdWithPessimisticLock(postId)
                .orElseThrow(PostNotFoundException::new);
        boolean alreadyLiked = postLikeRepository.existsByMemberAndPost(member, post);
        if (!alreadyLiked) {
            PostLike postLike = PostLike.builder()
                    .member(member)
                    .post(post)
                    .build();
            postLikeRepository.save(postLike);

            long likeCount = postLikeRepository.countByPostId(postId);

            if (likeCount >= HOT_BOARD_THRESHOLD && !post.isHotBoard()) {
                moveToHotBoard(post);
            }
        }
    }

    public void moveToHotBoard(Post post) {
        Community community = post.getBoard().getCommunity();
        Board hotboard = community.getBoards().stream()
                .filter(board -> board.getType() == BoardType.HOT)
                .findFirst()
                .orElse(null);
        if (hotboard != null) {
            post.setHotBoardTime();
            post.setHotBoard(true);
            hotboard.addPost(post);
        }
    }

    public void removeLike(Long postId) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(MemberNotFoundException::new);
        Post post = postRepository.findByIdWithPessimisticLock(postId)
                .orElseThrow(PostNotFoundException::new);

        PostLike postLike = postLikeRepository.findByMemberAndPost(member, post).orElseThrow(
                PostNotFoundException::new);

        authorizeMember(postLike);

        postLikeRepository.delete(postLike);
        long likeCount = postLikeRepository.countByPostId(postId);

        if (likeCount < HOT_BOARD_THRESHOLD && post.isHotBoard()) {
            removeFromHotBoard(post);
        }
        postRepository.save(post);
    }

    public void removeFromHotBoard(Post post) {
        Community community = post.getBoard().getCommunity();
        Board hotboard = community.getBoards().stream()
                .filter(board -> board.getType() == BoardType.HOT)
                .findFirst()
                .orElse(null);
        if (hotboard != null) {
            post.setHotBoard(false);
            hotboard.removePost(post);
        }
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
