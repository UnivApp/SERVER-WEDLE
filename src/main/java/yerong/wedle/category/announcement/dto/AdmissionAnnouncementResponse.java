package yerong.wedle.category.announcement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.category.announcement.domain.AnnouncementCategory;
import yerong.wedle.university.dto.UniversityResponse;

import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdmissionAnnouncementResponse {

    private String category;
    private String title;
    private String link;
    private String source;
    private List<String> relatedUniversityNames;
}
