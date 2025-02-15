package yerong.wedle.post.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.board.domain.Board;
import yerong.wedle.board.exception.BoardNotFoundException;
import yerong.wedle.board.repository.BoardRepository;
import yerong.wedle.like.postLike.repository.PostLikeRepository;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.repository.MemberRepository;
import yerong.wedle.post.domain.Post;
import yerong.wedle.post.dto.PostCreateRequest;
import yerong.wedle.post.dto.PostResponse;
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

    public PostResponse createPost(PostCreateRequest postRequest) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);

        Board board = boardRepository.findById(postRequest.getBoardId()).orElseThrow(BoardNotFoundException::new);
        System.out.println("board : " + board.getTitle());

        Post post = new Post(postRequest.getTitle(), postRequest.getContent(), postRequest.isAnonymous(), member,
                board);
        board.addPost(post);
        postRepository.save(post);
        return convertToPostResponse(post);
    }

    public PostResponse updatePost(PostUpdateRequest postUpdateRequest) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);

        Post post = postRepository.findById(postUpdateRequest.getPostId()).orElseThrow(PostNotFoundException::new);
        post.updateTitle(postUpdateRequest.getTitle());
        post.updateContent(postUpdateRequest.getContent());

        postRepository.save(post);

        return convertToPostResponse(post);
    }

    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        postRepository.delete(post);
    }

    public List<PostResponse> getAllPosts(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        return postRepository.findAllByBoard(board).stream()
                .map(this::convertToPostResponse)
                .collect(Collectors.toList());
    }

    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
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

        return new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.isAnonymous(), count, isLiked);
    }

    private String getCurrentUserId() {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();

        return socialId;
    }
}
