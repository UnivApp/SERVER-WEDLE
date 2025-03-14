package yerong.wedle.post.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.block.exception.AlreadyBlockedMemberException;
import yerong.wedle.block.service.BlockService;
import yerong.wedle.board.domain.Board;
import yerong.wedle.board.exception.BoardNotFoundException;
import yerong.wedle.board.repository.BoardRepository;
import yerong.wedle.community.domain.Community;
import yerong.wedle.like.postLike.repository.PostLikeRepository;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.exception.UnauthorizedAccessException;
import yerong.wedle.member.exception.UserBannedException;
import yerong.wedle.member.repository.MemberRepository;
import yerong.wedle.post.domain.Post;
import yerong.wedle.post.dto.HotPostResponse;
import yerong.wedle.post.dto.PostCreateRequest;
import yerong.wedle.post.dto.PostResponse;
import yerong.wedle.post.dto.PostUpdateAnonymousRequest;
import yerong.wedle.post.dto.PostUpdateRequest;
import yerong.wedle.post.exception.PostNotFoundException;
import yerong.wedle.post.repository.PostRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final PostLikeRepository postLikeRepository;
    private final BlockService blockService;

    public PostResponse createPost(PostCreateRequest postRequest) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);

        if (member.isBanned()) {
            throw new UserBannedException();
        }
        Board board = boardRepository.findById(postRequest.getBoardId()).orElseThrow(BoardNotFoundException::new);
        Post post = new Post(postRequest.getTitle(), postRequest.getContent(), postRequest.isAnonymous(), member,
                board);
        board.addPost(post);
        postRepository.save(post);
        return convertToPostResponse(post);
    }

    public PostResponse updatePost(PostUpdateRequest postUpdateRequest) {
        String socialId = getCurrentUserId();
        Post post = postRepository.findById(postUpdateRequest.getPostId()).orElseThrow(PostNotFoundException::new);

        if (!post.getMember().getSocialId().equals(socialId)) {
            throw new UnauthorizedAccessException();
        }

        post.updateTitle(postUpdateRequest.getTitle());
        post.updateContent(postUpdateRequest.getContent());

        postRepository.save(post);

        return convertToPostResponse(post);
    }

    public PostResponse updateAnonymous(PostUpdateAnonymousRequest postUpdateAnonymousRequest) {
        String socialId = getCurrentUserId();
        Post post = postRepository.findById(postUpdateAnonymousRequest.getPostId())
                .orElseThrow(PostNotFoundException::new);

        if (!post.getMember().getSocialId().equals(socialId)) {
            throw new UnauthorizedAccessException();
        }

        post.updateAnonymous(postUpdateAnonymousRequest.isAnonymous());

        postRepository.save(post);

        return convertToPostResponse(post);
    }

    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        String socialId = getCurrentUserId();

        if (!post.getMember().getSocialId().equals(socialId)) {
            throw new UnauthorizedAccessException();
        }

        post.getComments().clear();
        postRepository.delete(post);
    }

    public List<PostResponse> getAllPosts(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);

        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(MemberNotFoundException::new);

        List<Post> posts = postRepository.findAllByBoardOrderByCreatedAtDesc(board);
        posts.removeIf(post ->
                blockService.isBlocked(member.getMemberId(), post.getMember().getMemberId()) ||  // 내가 그 사람 차단했거나
                        blockService.isBlocked(post.getMember().getMemberId(), member.getMemberId())
        );
        return posts.stream()
                .map(this::convertToPostResponse)
                .collect(Collectors.toList());
    }

    public List<HotPostResponse> getAllHotPosts() {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(MemberNotFoundException::new);
        Community community = member.getSchool().getCommunity();

        List<Post> hotPosts = postRepository.findAllByBoard_CommunityAndIsHotBoardTrueOrderByHotBoardTimeDesc(
                community);
        hotPosts.removeIf(post ->
                        blockService.isBlocked(member.getMemberId(), post.getMember().getMemberId()) ||  // 내가 그 사람 차단했거나
                                blockService.isBlocked(post.getMember().getMemberId(), member.getMemberId())
                // 그 사람이 나를 차단했으면
        );
        return hotPosts.stream()
                .map(this::convertToHotPostResponse)
                .collect(Collectors.toList());
    }


    public PostResponse getPost(Long postId) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(MemberNotFoundException::new);

        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        if (blockService.isBlocked(member.getMemberId(), post.getMember().getMemberId()) ||
                blockService.isBlocked(post.getMember().getMemberId(), member.getMemberId())) {
            throw new AlreadyBlockedMemberException();
        }
        return convertToPostResponse(post);
    }

    public Long likeCount(Long postId) {
        return postLikeRepository.countByPostId(postId);
    }

    public boolean isLiked(Long postId) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(MemberNotFoundException::new);
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        return postLikeRepository.existsByMemberAndPost(member, post);
    }

    private PostResponse convertToPostResponse(Post post) {
        Long count = likeCount(post.getId());
        boolean isLiked = isLiked(post.getId());
        String username;
        if (post.isAnonymous()) {
            username = null;
        } else {
            username = post.getMember().getUsername();
        }
        return new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.isAnonymous(), count, isLiked,
                username);
    }

    private HotPostResponse convertToHotPostResponse(Post post) {
        Long count = likeCount(post.getId());
        boolean isLiked = isLiked(post.getId());
        String username;
        if (post.isAnonymous()) {
            username = null;
        } else {
            username = post.getMember().getUsername();
        }
        return new HotPostResponse(post.getId(), post.getTitle(), post.getContent(), post.isAnonymous(), count, isLiked,
                post.getBoard().getType().getName(), username);
    }

    private String getCurrentUserId() {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();

        return socialId;
    }

    public long getMemberIdByPostId(Long targetId) {
        Post post = postRepository.findById(targetId).orElseThrow(PostNotFoundException::new);
        return post.getMember().getMemberId();
    }

    public void deletePostByReport(Long postId) {
        postRepository.deleteById(postId);
    }
}

