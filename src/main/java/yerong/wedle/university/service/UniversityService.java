package yerong.wedle.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.star.repository.StarRepository;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.dto.UniversityAllResponse;
import yerong.wedle.university.dto.UniversityResponse;
import yerong.wedle.university.exception.UniversityNotFoundException;
import yerong.wedle.university.repository.UniversityRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UniversityService {

    private final UniversityRepository universityRepository;
    private final StarRepository starRepository;

    @Transactional
    public List<UniversityResponse> searchUniversitiesSummary(String keyward) {
        List<University> universities = universityRepository.findByNameContainingOrLocationContaining(keyward, keyward);

        if (universities.isEmpty()) {
            throw new UniversityNotFoundException();
        }

        return universities.stream()
                .map(this::convertToSummaryDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UniversityResponse getUniversitySummaryById(Long universityId) {
        University university = universityRepository.findById(universityId).orElseThrow(UniversityNotFoundException::new);
        return convertToSummaryDto(university);
    }
    @Transactional
    public UniversityAllResponse getUniversityDetailsById(Long universityId) {
        University university = universityRepository.findById(universityId).orElseThrow(UniversityNotFoundException::new);
        return convertToDetailDto(university);
    }
    @Transactional
    public List<UniversityResponse> getAllUniversitiesSummary() {
        List<University> universities = universityRepository.findAll();
        return universities.stream()
                .map(this::convertToSummaryDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<UniversityAllResponse> getAllUniversitiesDetails() {
        List<University> universities = universityRepository.findAll();
        return universities.stream()
                .map(this::convertToDetailDto)
                .collect(Collectors.toList());
    }
    private UniversityResponse convertToSummaryDto(University university) {
        Long starNum = starRepository.countByUniversityId(university.getUniversityId());  // 관심 설정된 횟수 계산
        String nameWithCampus = formatNameWithCampus(university.getName(), university.getCampus());

        return new UniversityResponse(
                nameWithCampus,
                university.getLogo(),
                starNum
        );
    }
    private UniversityAllResponse convertToDetailDto(University university) {
        Long starNum = starRepository.countByUniversityId(university.getUniversityId());  // 관심 설정된 횟수 계산
        String nameWithCampus = formatNameWithCampus(university.getName(), university.getCampus());

        return new UniversityAllResponse(
                nameWithCampus,
                university.getLocation(),
                university.getType(),
                university.getLogo(),
                university.getPhoneNumber(),
                university.getWebsite(),
                starNum
        );
    }

    private String formatNameWithCampus(String name, String campus) {
        if (campus != null && !campus.isEmpty()) {
            return name + " (" + campus + ")";
        }
        return name;
    }
}
