package yerong.wedle.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.competitionRate.domain.CompetitionRate;
import yerong.wedle.competitionRate.dto.CompetitionRateResponse;
import yerong.wedle.competitionRate.repository.CompetitionRateRepository;
import yerong.wedle.department.domain.Department;
import yerong.wedle.department.dto.DepartmentResponse;
import yerong.wedle.department.repository.DepartmentRepository;
import yerong.wedle.employmentRate.domain.EmploymentRate;
import yerong.wedle.employmentRate.dto.EmploymentRateResponse;
import yerong.wedle.employmentRate.repository.EmploymentRateRepository;
import yerong.wedle.employmentRate.service.EmploymentRateService;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
    private final EmploymentRateRepository employmentRateRepository;
    private final CompetitionRateRepository competitionRateRepository;
    private final DepartmentRepository departmentRepository;
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
        Long starNum = starRepository.countByUniversityId(university.getUniversityId());
        String socialId = getCurrentUserId();

        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);

        boolean isStarred = starRepository.existsByMemberAndUniversity(member, university);

        return new UniversityResponse(
                university.getUniversityId(),
                university.getName(),
                university.getLogo(),
                starNum,
                isStarred
        );
    }
    private UniversityAllResponse convertToDetailDto(University university) {
        Long starNum = starRepository.countByUniversityId(university.getUniversityId());  // 관심 설정된 횟수 계산

        List<CompetitionRate> competitionRates = competitionRateRepository.findByUniversity(university);
        List<EmploymentRate> employmentRates = employmentRateRepository.findByUniversity(university); // EmploymentRate 데이터를 가져옵니다.
        List<Department> departments = departmentRepository.findByUniversity(university);

        List<CompetitionRateResponse> competitionRateResponses = competitionRates.stream()
                .map(rate -> new CompetitionRateResponse(rate.getEarlyAdmissionRate(), rate.getRegularAdmissionRate(), rate.getCompetitionYear()))
                .collect(Collectors.toList());

        List<EmploymentRateResponse> employmentRateResponses = employmentRates.stream()
                .map(rate -> new EmploymentRateResponse(rate.getEmploymentRate(), rate.getEmploymentYear()))
                .collect(Collectors.toList());

        List<DepartmentResponse> departmentResponses = departments.stream()
                .map(department -> new DepartmentResponse(department.getName(), department.getDepartmentType().getDisplayName()))
                .collect(Collectors.toList());

        return new UniversityAllResponse(
                university.getUniversityId(),
                university.getName(),
                university.getLocation(),
                university.getType(),
                university.getLogo(),
                university.getPhoneNumber(),
                university.getWebsite(),
                university.getAdmissionSite(),
                starNum,
                departmentResponses,
                competitionRateResponses,
                employmentRateResponses
        );
    }
    private String getCurrentUserId() {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();

        return socialId;
    }
}
