package yerong.wedle.entranceScore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.entranceScore.domain.EntranceScore;
import yerong.wedle.entranceScore.dto.EntranceScoreResponse;
import yerong.wedle.entranceScore.repository.EntranceScoreRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EntranceScoreService {

    private final EntranceScoreRepository entranceScoreRepository;

    @Transactional(readOnly = true)
    public EntranceScoreResponse getImageByType(String type) {
        Optional<EntranceScore> entranceScoreImage = entranceScoreRepository.findByType(type);
        return entranceScoreImage
                .map(image -> new EntranceScoreResponse(image.getType(), image.getImage()))
                .orElseThrow(() -> new IllegalArgumentException("입결 이미지 정보를 찾을 수 없습니다."));
    }
}
