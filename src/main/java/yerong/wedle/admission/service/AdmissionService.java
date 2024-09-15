package yerong.wedle.admission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.admission.domain.Admission;
import yerong.wedle.admission.domain.AdmissionType;
import yerong.wedle.admission.dto.AdmissionResponse;
import yerong.wedle.admission.repository.AdmissionRepository;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.repository.MemberRepository;
import yerong.wedle.star.repository.StarRepository;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.exception.UniversityNotFoundException;
import yerong.wedle.university.repository.UniversityRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdmissionService {

    private final AdmissionRepository admissionRepository;
    private final StarRepository starRepository;
    private final UniversityRepository universityRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<AdmissionResponse> getTop4EarlyAdmissions() {
        return admissionRepository.findTop4ByAdmissionTypeOrderByRankAsc(AdmissionType.EARLY)
                .stream()
                .map(this::convertToAdmissionResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AdmissionResponse> getTop4RegularAdmissions() {
        return admissionRepository.findTop4ByAdmissionTypeOrderByRankAsc(AdmissionType.REGULAR)
                .stream()
                .map(this::convertToAdmissionResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AdmissionResponse> getAllEarlyAdmissions() {
        return admissionRepository.findByAdmissionTypeOrderByRankAsc(AdmissionType.EARLY)
                .stream()
                .map(this::convertToAdmissionResponse)
                .collect(Collectors.toList());
    }

    // 전체 정시 조회
    @Transactional(readOnly = true)
    public List<AdmissionResponse> getAllRegularAdmissions() {
        return admissionRepository.findByAdmissionTypeOrderByRankAsc(AdmissionType.REGULAR)
                .stream()
                .map(this::convertToAdmissionResponse)
                .collect(Collectors.toList());
    }

    private AdmissionResponse convertToAdmissionResponse(Admission admission) {

        String socialId = getCurrentUserId();

        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);

        University university = universityRepository.findByName(admission.getUniversity().getName()).orElseThrow(UniversityNotFoundException::new);
        boolean isStarred = starRepository.existsByMemberAndUniversity(member, university);

        return new AdmissionResponse(
                admission.getUniversity().getName(),
                admission.getUniversity().getLocation(),
                admission.getAdmissionType().getDisplayName(),
                admission.getRank(),
                admission.getPercentile(),
                isStarred

        );
    }

    private String getCurrentUserId() {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();

        return socialId;
    }
}
