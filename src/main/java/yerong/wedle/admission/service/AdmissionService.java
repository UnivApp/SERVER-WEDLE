package yerong.wedle.admission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.admission.domain.Admission;
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
    public List<AdmissionResponse> getAllAdmissionsSortedByUniversityId() {
        return admissionRepository.findAllByOrderByUniversityIdAsc()
                .stream()
                .map(this::convertToAdmissionResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AdmissionResponse> getCompetitionRates() {
        return admissionRepository.findAllByOrderByUniversity_IdAsc()
                .stream()
                .map(this::convertToAdmissionResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AdmissionResponse> getEmploymentRates() {
        return admissionRepository.findAllByOrderByUniversity_IdAsc()
                .stream()
                .map(this::convertToAdmissionResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AdmissionResponse> getCompetitionAndEmploymentRates() {
        return admissionRepository.findAllByOrderByUniversity_IdAsc()
                .stream()
                .map(this::convertToAdmissionResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AdmissionResponse> getCompetitionAndNumbers() {
        return admissionRepository.findAllByOrderByUniversity_IdAsc()
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
                isStarred,
                admission.getCompetitionRate(),
                admission.getEmploymentRate(),
                admission.getRecruitmentNumber(),
                admission.getApplicantNumber()
        );
    }

    private String getCurrentUserId() {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();
        return socialId;
    }

    private boolean isStarred(Admission admission) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(MemberNotFoundException::new);
        University university = universityRepository.findByName(admission.getUniversity().getName()).orElseThrow(UniversityNotFoundException::new);
        return starRepository.existsByMemberAndUniversity(member, university);
    }
}
