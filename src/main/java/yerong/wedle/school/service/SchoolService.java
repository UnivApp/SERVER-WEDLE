package yerong.wedle.school.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.community.service.CommunityService;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.repository.MemberRepository;
import yerong.wedle.school.domain.School;
import yerong.wedle.school.dto.SchoolRegistrationRequest;
import yerong.wedle.school.exception.SchoolChangeNotAllowedException;
import yerong.wedle.school.neis.NeisApiClient;
import yerong.wedle.school.neis.NeisSchoolResponse;
import yerong.wedle.school.repository.SchoolRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class SchoolService {
    private final SchoolRepository schoolRepository;
    private final MemberRepository memberRepository;
    private final NeisApiClient neisApiClient;
    private final CommunityService communityService;

    public List<NeisSchoolResponse> searchSchool(String keyword) {
        List<NeisSchoolResponse> neisSchools = neisApiClient.searchSchool(keyword);
        return neisSchools;
    }

    public void setSchool(SchoolRegistrationRequest schoolRegistrationRequest) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);

        Optional<School> schoolOpt = schoolRepository.findBySchoolCode(schoolRegistrationRequest.getSchoolCode());
        School school;
        if (schoolOpt.isEmpty()) {
            school = School.builder()
                    .name(schoolRegistrationRequest.getName())
                    .atptCode(schoolRegistrationRequest.getAtpt())
                    .schoolCode(schoolRegistrationRequest.getSchoolCode())
                    .address(schoolRegistrationRequest.getAddress())
                    .phone(schoolRegistrationRequest.getPhone())
                    .hompage(schoolRegistrationRequest.getHompage())
                    .build();
            schoolRepository.save(school);
            communityService.createCommunityIfNotExists(school.getId());
        } else {
            school = schoolOpt.get();
        }

        if (member.getSchool() != null && member.getSchoolRegisteredDate() != null) {
            long monthSinceRegistered = ChronoUnit.MONTHS.between(member.getSchoolRegisteredDate(),
                    LocalDateTime.now());
            if (monthSinceRegistered < 12) {
                throw new SchoolChangeNotAllowedException();
            }
        }
        member.setSchool(school);
        member.setSchoolRegisteredDate(LocalDate.now());
        memberRepository.save(member);
    }

    private String getCurrentUserId() {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();

        return socialId;
    }
}
