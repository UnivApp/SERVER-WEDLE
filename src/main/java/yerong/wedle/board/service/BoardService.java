package yerong.wedle.board.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import yerong.wedle.board.domain.Board;
import yerong.wedle.board.dto.BoardRequest;
import yerong.wedle.board.dto.BoardResponse;
import yerong.wedle.board.exception.BoardNotFoundException;
import yerong.wedle.board.repository.BoardRepository;
import yerong.wedle.community.domain.Community;
import yerong.wedle.community.repository.CommunityRepository;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.repository.MemberRepository;
import yerong.wedle.school.repository.SchoolRepository;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final CommunityRepository communityRepository;
    private final SchoolRepository schoolRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public Board createBoard(BoardRequest boardRequest) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);
        Community community = communityRepository.findById(member.getSchool().getCommunity().getId()).orElseThrow(
                BoardNotFoundException::new);
        Board board = new Board(boardRequest.getTitle(), community);
        community.addBoard(board);
        return boardRepository.save(board);
    }

    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
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
        return new BoardResponse(board.getId(), board.getTitle(), board.getCommunity().getId());
    }

    private String getCurrentUserId() {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();

        return socialId;
    }
}
