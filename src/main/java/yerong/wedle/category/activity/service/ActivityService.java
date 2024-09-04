package yerong.wedle.category.activity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.category.activity.domain.Activity;
import yerong.wedle.category.activity.dto.ActivityResponse;
import yerong.wedle.category.activity.repository.ActivityRepository;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.exception.UniversityNotFoundException;
import yerong.wedle.university.repository.UniversityRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UniversityRepository universityRepository;

    @Transactional
    public List<ActivityResponse> getActivitiesByUniversityName(String universityName) {
        University university = universityRepository.findByName(universityName)
                .orElseThrow(UniversityNotFoundException::new);
        List<Activity> activities = activityRepository.findByUniversity(university);
        return activities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    private ActivityResponse convertToDto(Activity activity) {
        return new ActivityResponse(
                activity.getName(),
                activity.getDescription(),
                activity.getLocation()
        );
    }
}
