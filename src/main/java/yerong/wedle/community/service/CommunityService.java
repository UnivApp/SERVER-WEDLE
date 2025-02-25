package yerong.wedle.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.board.domain.Board;
import yerong.wedle.board.domain.BoardType;
import yerong.wedle.board.repository.BoardRepository;
import yerong.wedle.community.domain.Community;
import yerong.wedle.community.repository.CommunityRepository;
import yerong.wedle.school.domain.School;
import yerong.wedle.school.exception.SchoolNotFoundException;
import yerong.wedle.school.repository.SchoolRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final SchoolRepository schoolRepository;
    private final BoardRepository boardRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public void createCommunityIfNotExists(Long schoolId) {
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(SchoolNotFoundException::new);

        boolean exists = communityRepository.existsBySchool(school);
        if (!exists) {
            Community community = new Community(school);
            communityRepository.save(community);
            initializeDefaultBoards(community);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void initializeDefaultBoards(Community community) {
        createDefaultBoard("자유게시판", community, BoardType.FREE);
        createDefaultBoard("정보게시판", community, BoardType.INFORMATION);
        createDefaultBoard("비밀게시판", community, BoardType.SECRET);
        createDefaultBoard("HOT게시판", community, BoardType.HOT);
    }

    private void createDefaultBoard(String title, Community community, BoardType boardType) {
        Board board = new Board(title, community, boardType, null);
        community.addBoard(board);
        boardRepository.save(board);
    }
}
