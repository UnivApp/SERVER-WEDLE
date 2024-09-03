package yerong.wedle.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.star.repository.StarRepository;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.dto.UniversityResponse;
import yerong.wedle.university.repository.UniversityRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UniversityService {

    private final UniversityRepository universityRepository;
    private final StarRepository starRepository;

    @Transactional
    public List<UniversityResponse> searchUniversity(String keyward) {
        List<University> universities = universityRepository.findByNameContainingOrLocationContaining(keyward, keyward);
        return universities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UniversityResponse getUniversityById(Long universityId) {
        University university = universityRepository.findById(universityId).orElseThrow(() -> new IllegalArgumentException("대학교를 찾을 수 없습니다."));
        return convertToDto(university);
    }

    @Transactional
    public List<UniversityResponse> getAllUniversities() {
        List<University> universities = universityRepository.findAll();
        return universities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    private UniversityResponse convertToDto(University university) {
        Long starNum = starRepository.countByUniversityId(university.getUniversityId());  // 관심 설정된 횟수 계산
        return new UniversityResponse(
                university.getName(),
                university.getLocation(),
                university.getLogo(),
                starNum
        );
    }
}
