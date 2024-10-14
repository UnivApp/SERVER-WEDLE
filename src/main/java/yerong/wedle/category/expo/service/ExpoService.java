package yerong.wedle.category.expo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.category.expo.domain.Expo;
import yerong.wedle.category.expo.domain.ExpoStatus;
import yerong.wedle.category.expo.domain.ExpoType;
import yerong.wedle.category.expo.dto.ExpoResponse;
import yerong.wedle.category.expo.repository.ExpoRepository;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.repository.UniversityRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ExpoService {

    private final ExpoRepository expoRepository;
    private final UniversityRepository universityRepository;
    public List<ExpoResponse> getExposByKeyword(String keyword) {
        List<Expo> expos = expoRepository.findByContentContainingOrTitleContaining(keyword, keyword);

        return expos.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public List<ExpoResponse> getExposByStatus(ExpoStatus status) {
        return expoRepository.findByExpoStatus(status).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public List<ExpoResponse> getExposByType(ExpoType type) {

        return expoRepository.findByExpoType(type).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ExpoResponse> getAllExpos() {
        return expoRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    private ExpoResponse convertToDto(Expo expo) {

        return new ExpoResponse(
                expo.getId(),
                expo.getTitle(),
                expo.getExpoType().getDisplayName(),
                expo.getExpoYear(),
                expo.getExpoStatus().getDisplayName(),
                expo.getLink(),
                expo.getLocation(),
                expo.getContent(),
                expo.getStartDate().toString() + " ~ " + expo.getEndDate().toString()
        );
    }
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void updateExpiredExposStatus() {
        LocalDate today = LocalDate.now();
        List<Expo> expiredExpos = expoRepository.findByEndDateBeforeAndExpoStatus(today, ExpoStatus.OPEN);

        for (Expo expo : expiredExpos) {
            expo.setExpoStatus(ExpoStatus.CLOSED);
        }
    }
}