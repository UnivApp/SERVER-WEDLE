package yerong.wedle.category.alumni.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.category.alumni.domain.Alumni;
import yerong.wedle.category.alumni.dto.AlumniResponse;
import yerong.wedle.category.alumni.repository.AlumniRepository;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.exception.UniversityNotFoundException;
import yerong.wedle.university.repository.UniversityRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AlumniService {

    private final AlumniRepository alumniRepository;
    private final UniversityRepository universityRepository;

    @Transactional
    public List<AlumniResponse> getAlumniByUniversityName(String universityName) {
        University university = universityRepository.findByName(universityName)
                .orElseThrow(UniversityNotFoundException::new);
        List<Alumni> alumni = alumniRepository.findByUniversity(university);
        return alumni.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    private AlumniResponse convertToDto(Alumni alumni) {
        return new AlumniResponse(
                alumni.getName(),
                alumni.getDegree(),
                alumni.getDepartment(),
                alumni.getAchievements(),
                alumni.getAlumniCategory().getDisplayName(),
                alumni.getImageUrl()
        );
    }
}
