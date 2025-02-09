package yerong.wedle.school.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.repository.MemberRepository;
import yerong.wedle.school.domain.School;
import yerong.wedle.school.dto.SchoolRegistrationRequest;
import yerong.wedle.school.dto.SchoolResponse;
import yerong.wedle.school.exception.SchoolChangeNotAllowedException;
import yerong.wedle.school.exception.SchoolNotFoundException;
import yerong.wedle.school.repository.SchoolRepository;

@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;
    private final MemberRepository memberRepository;

    public List<SchoolResponse> searchSchool(String keyword) {
        List<School> schools = schoolRepository.findByNameContaining(keyword);
        return schools.stream()
                .map(school -> new SchoolResponse(school.getId(), school.getName(), school.getAddress()))
                .collect(Collectors.toList());
    }

    public void setSchool(SchoolRegistrationRequest schoolRegistrationRequest) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);

        School school = schoolRepository.findById(schoolRegistrationRequest.getSchoolId())
                .orElseThrow(SchoolNotFoundException::new);

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
