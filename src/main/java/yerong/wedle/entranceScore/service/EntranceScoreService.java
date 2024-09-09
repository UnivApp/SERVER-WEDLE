package yerong.wedle.entranceScore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.entranceScore.domain.EntranceScore;
import yerong.wedle.entranceScore.dto.EntranceScoreResponse;
import yerong.wedle.entranceScore.exception.EntranceScoreNotFoundException;
import yerong.wedle.entranceScore.repository.EntranceScoreRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EntranceScoreService {

    private final EntranceScoreRepository entranceScoreRepository;

    @Transactional(readOnly = true)
    public EntranceScoreResponse getImage() {
        EntranceScore entranceScore = entranceScoreRepository.findAll().get(0);
        return new EntranceScoreResponse(entranceScore.getImage());
    }
}
