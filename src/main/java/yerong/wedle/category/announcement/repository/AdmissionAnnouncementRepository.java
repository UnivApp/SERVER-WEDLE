package yerong.wedle.category.announcement.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.category.announcement.domain.AdmissionAnnouncement;
import yerong.wedle.category.announcement.domain.AnnouncementCategory;
import yerong.wedle.university.domain.University;

import java.util.Arrays;
import java.util.List;

public interface AdmissionAnnouncementRepository extends JpaRepository<AdmissionAnnouncement, Long> {
    List<AdmissionAnnouncement> findByRelatedUniversities(University university);

    List<AdmissionAnnouncement> findByCategory(AnnouncementCategory category);
}
