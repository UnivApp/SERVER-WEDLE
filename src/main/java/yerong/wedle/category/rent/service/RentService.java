package yerong.wedle.category.rent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.category.rent.domain.Rent;
import yerong.wedle.category.rent.dto.RentResponse;
import yerong.wedle.category.rent.repository.RentRepository;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.repository.UniversityRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RentService {

    private final RentRepository rentRepository;
    private final UniversityRepository universityRepository;

    @Transactional
    public List<RentResponse> getRentsByUniversityName(String universityName) {
        University university = universityRepository.findByName(universityName)
                .orElseThrow(() -> new IllegalArgumentException("대학교를 찾을 수 없습니다."));
        List<Rent> rents = rentRepository.findByUniversity(university);
        return rents.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private RentResponse convertToDto(Rent rent) {
        return new RentResponse(
                rent.getArea(),
                rent.getAverageRent(),
                rent.getSource()
        );
    }
}