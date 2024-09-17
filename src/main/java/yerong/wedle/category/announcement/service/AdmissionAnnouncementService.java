package yerong.wedle.category.announcement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.category.announcement.domain.AdmissionAnnouncement;
import yerong.wedle.category.announcement.domain.AnnouncementCategory;
import yerong.wedle.category.announcement.dto.AdmissionAnnouncementResponse;
import yerong.wedle.category.announcement.exception.AdmissionAnnouncementCategoryNotFoundException;
import yerong.wedle.category.announcement.exception.AdmissionAnnouncementNotFoundException;
import yerong.wedle.category.announcement.repository.AdmissionAnnouncementRepository;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.dto.UniversityResponse;
import yerong.wedle.university.exception.UniversityNotFoundException;
import yerong.wedle.university.repository.UniversityRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AdmissionAnnouncementService {

    private final AdmissionAnnouncementRepository announcementRepository;
    private final UniversityRepository universityRepository;


    public List<AdmissionAnnouncementResponse> getAnnouncementsByUniversity(Long universityId) {
        University university = universityRepository.findById(universityId)
                .orElseThrow(UniversityNotFoundException::new);
        return announcementRepository.findByRelatedUniversities(university).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public List<AdmissionAnnouncementResponse> getAnnouncementsByCategory(String categoryDisplayName) {
        AnnouncementCategory category = Arrays.stream(AnnouncementCategory.values())
                .filter(c -> c.getDisplayName().equals(categoryDisplayName))
                .findFirst()
                .orElseThrow(AdmissionAnnouncementCategoryNotFoundException::new);
        return announcementRepository.findByCategory(category).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<AdmissionAnnouncementResponse> getAllAnnouncements() {
        return announcementRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private AdmissionAnnouncementResponse convertToDto(AdmissionAnnouncement announcement) {
        List<String> relatedUniversityNames = announcement.getRelatedUniversities().stream()
                .map(University::getName)
                .collect(Collectors.toList());

        return new AdmissionAnnouncementResponse(
                announcement.getCategory().getDisplayName(),
                announcement.getTitle(),
                announcement.getLink(),
                announcement.getSource(),
                relatedUniversityNames
        );
    }
}