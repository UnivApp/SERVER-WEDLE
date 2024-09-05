package yerong.wedle.category.mou.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.category.mou.domain.Mou;
import yerong.wedle.category.mou.dto.MouResponse;
import yerong.wedle.category.mou.repository.MouRepository;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.exception.UniversityNotFoundException;
import yerong.wedle.university.repository.UniversityRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MouService {

    private final MouRepository mouRepository;
    private final UniversityRepository universityRepository;

    @Transactional
    public List<MouResponse> getMousByUniversityName(String universityName) {
        University university = universityRepository.findByName(universityName)
                .orElseThrow(UniversityNotFoundException::new);
        List<Mou> mous = mouRepository.findByUniversity(university);
        return mous.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private MouResponse convertToDto(Mou mou) {
        return new MouResponse(
                mou.getPartnerInstitute(),
                mou.getDescription(),
                mou.getMouCategory().getDisplayName(),
                mou.getAgreementDate(),
                mou.getDepartment()
        );
    }
}