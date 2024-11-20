package yerong.wedle.university.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.competitionRate.domain.CompetitionRate;
import yerong.wedle.competitionRate.dto.CompetitionRateResponse;
import yerong.wedle.competitionRate.repository.CompetitionRateRepository;
import yerong.wedle.department.domain.Department;
import yerong.wedle.department.domain.DepartmentType;
import yerong.wedle.department.dto.DepartmentResponse;
import yerong.wedle.department.repository.DepartmentRepository;
import yerong.wedle.employmentRate.domain.EmploymentRate;
import yerong.wedle.employmentRate.dto.EmploymentRateResponse;
import yerong.wedle.employmentRate.repository.EmploymentRateRepository;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.repository.MemberRepository;
import yerong.wedle.star.repository.StarRepository;
import yerong.wedle.tuitionfee.domain.TuitionFee;
import yerong.wedle.tuitionfee.dto.TuitionFeeResponse;
import yerong.wedle.tuitionfee.dto.YearTuitionFeeResponse;
import yerong.wedle.tuitionfee.repository.TuitionFeeRepository;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.dto.UniversityAllResponse;
import yerong.wedle.university.dto.UniversityResponse;
import yerong.wedle.university.exception.UniversityNotFoundException;
import yerong.wedle.university.repository.UniversityRepository;

@RequiredArgsConstructor
@Service
public class UniversityService {

    private final UniversityRepository universityRepository;
    private final StarRepository starRepository;
    private final MemberRepository memberRepository;
    private final EmploymentRateRepository employmentRateRepository;
    private final CompetitionRateRepository competitionRateRepository;
    private final DepartmentRepository departmentRepository;
    private final TuitionFeeRepository tuitionFeeRepository;

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
        University university = universityRepository.findById(universityId)
                .orElseThrow(UniversityNotFoundException::new);
        return convertToSummaryDto(university);
    }

    @Transactional
    public UniversityAllResponse getUniversityDetailsById(Long universityId) {
        University university = universityRepository.findById(universityId)
                .orElseThrow(UniversityNotFoundException::new);
        return convertToDetailDto(university);
    }

    @Transactional
    public List<UniversityResponse> getAllUniversitiesSummary() {
        List<University> universities = universityRepository.findAllByOrderByNameAsc();
        return universities.stream()
                .map(this::convertToSummaryDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<UniversityAllResponse> getAllUniversitiesDetails() {
        List<University> universities = universityRepository.findAllByOrderByNameAsc();
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
        Long starNum = starRepository.countByUniversityId(university.getUniversityId());

        List<TuitionFee> tuitionFees = tuitionFeeRepository.findByUniversity(university);
        List<CompetitionRate> competitionRates = competitionRateRepository.findByUniversity(university);
        List<EmploymentRate> employmentRates = employmentRateRepository.findByUniversity(university);
        List<Department> departments = departmentRepository.findByUniversity(university);

        List<YearTuitionFeeResponse> allTuitionFeeResponses = getAllTuitionFeeResponses(tuitionFees);

        List<CompetitionRateResponse> competitionRateResponses = competitionRates.stream()
                .map(rate -> new CompetitionRateResponse(rate.getEarlyAdmissionRate(), rate.getRegularAdmissionRate(),
                        rate.getAverageAdmissionRate(), rate.getCompetitionYear()))
                .collect(Collectors.toList());

        List<EmploymentRateResponse> employmentRateResponses = employmentRates.stream()
                .map(rate -> new EmploymentRateResponse(rate.getEmploymentYear(), rate.getEmploymentRate()))
                .collect(Collectors.toList());

        List<DepartmentResponse> departmentResponses = departments.stream()
                .collect(Collectors.groupingBy(Department::getDepartmentType))
                .entrySet().stream()
                .sorted(Comparator.comparing(entry -> entry.getKey().ordinal()))
                .map(entry -> convertToDepartmentDto(entry.getKey(), entry.getValue()))
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
                allTuitionFeeResponses,
                departmentResponses,
                competitionRateResponses,
                employmentRateResponses
        );
    }

    private List<YearTuitionFeeResponse> getAllTuitionFeeResponses(List<TuitionFee> tuitionFees) {
        Map<String, List<TuitionFee>> groupedByYear = tuitionFees.stream()
                .collect(Collectors.groupingBy(TuitionFee::getTuitionFeeYear));

        return groupedByYear.entrySet().stream()
                .map(entry -> {
                    String year = entry.getKey();
                    List<TuitionFeeResponse> tuitionFeeResponses = entry.getValue().stream()
                            .map(tuitionFee -> new TuitionFeeResponse(
                                    tuitionFee.getTuitionFeeType().getDisplayName(),
                                    tuitionFee.getFeeAmount()))
                            .collect(Collectors.toList());
                    return new YearTuitionFeeResponse(year, tuitionFeeResponses);
                })
                .collect(Collectors.toList());
    }

    private DepartmentResponse convertToDepartmentDto(DepartmentType departmentType, List<Department> departments) {
        List<String> departmentNames = departments.stream()
                .map(Department::getName)
                .collect(Collectors.toList());

        return new DepartmentResponse(
                departmentType.getDisplayName(),
                departmentNames
        );
    }

    private String getCurrentUserId() {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();

        return socialId;
    }
}
