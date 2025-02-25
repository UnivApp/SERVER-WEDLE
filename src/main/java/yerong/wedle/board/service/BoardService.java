package yerong.wedle.board.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.board.domain.Board;
import yerong.wedle.board.domain.BoardType;
import yerong.wedle.board.dto.BoardRequest;
import yerong.wedle.board.dto.BoardResponse;
import yerong.wedle.board.exception.BoardDuplicateException;
import yerong.wedle.board.exception.BoardNotFoundException;
import yerong.wedle.board.repository.BoardRepository;
import yerong.wedle.community.domain.Community;
import yerong.wedle.community.repository.CommunityRepository;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.exception.UnauthorizedAccessException;
import yerong.wedle.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final CommunityRepository communityRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public BoardResponse createBoard(BoardRequest boardRequest) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);
        Community community = communityRepository.findById(member.getSchool().getCommunity().getId()).orElseThrow(
                BoardNotFoundException::new);

        if (boardRepository.existsByTitleAndCommunity(boardRequest.getTitle(), community)) {
            throw new BoardDuplicateException();
        }

        Board board = new Board(boardRequest.getTitle(), community, BoardType.CUSTOM, member);
        community.addBoard(board);
        boardRepository.save(board);
        return convertToBoardResponse(board);
    }

    public void deleteBoard(Long boardId) {
        String socialId = getCurrentUserId();

        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        if (!board.getMember().getSocialId().equals(socialId)) {
            throw new UnauthorizedAccessException();
        }
        boardRepository.delete(board);
    }

    public List<BoardResponse> getBoards() {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);
        Community community = communityRepository.findById(member.getSchool().getCommunity().getId()).orElseThrow(
                BoardNotFoundException::new);

        return boardRepository.findAllByCommunity(community).stream()
                .map(this::convertToBoardResponse)
                .collect(Collectors.toList());
    }

    public BoardResponse getBoardById(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        return convertToBoardResponse(board);
    }

    private BoardResponse convertToBoardResponse(Board board) {
        return new BoardResponse(board.getId(), board.getTitle());
    }

    private String getCurrentUserId() {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();
        return socialId;
    }
}
