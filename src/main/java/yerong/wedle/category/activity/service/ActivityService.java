package yerong.wedle.category.activity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.category.activity.domain.Activity;
import yerong.wedle.category.activity.dto.ActivityImageResponse;
import yerong.wedle.category.activity.dto.ActivityResponse;
import yerong.wedle.category.activity.repository.ActivityRepository;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.exception.UniversityNotFoundException;
import yerong.wedle.university.repository.UniversityRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UniversityRepository universityRepository;

    @Transactional
    public List<ActivityResponse> getActivitiesByUniversityId(Long universityId) {
        University university = universityRepository.findById(universityId)
                .orElseThrow(UniversityNotFoundException::new);
        List<Activity> activities = activityRepository.findByUniversities_University(university);
        return activities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ActivityResponse> getActivitiesByIds() {
        List<Long> activityIds = Arrays.asList(2L, 4L, 6L, 8L, 10L, 12L, 25L, 37L, 38L, 50L);

        List<Activity> activities = activityRepository.findByActivityIdIn(activityIds);

        return activities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    private ActivityResponse convertToDto(Activity activity) {
        List<ActivityImageResponse> images = activity.getImages().stream()
                .map(image -> new ActivityImageResponse(image.getImageUrl(), image.getSource()))
                .collect(Collectors.toList());

        return new ActivityResponse(
                activity.getName(),
                activity.getDescription(),
                activity.getTip(),
                activity.getLocation(),
                images
        );
    }
}
