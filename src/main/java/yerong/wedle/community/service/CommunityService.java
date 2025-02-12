package yerong.wedle.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.community.domain.Community;
import yerong.wedle.community.repository.CommunityRepository;
import yerong.wedle.school.domain.School;
import yerong.wedle.school.dto.SchoolRequest;
import yerong.wedle.school.exception.SchoolNotFoundException;
import yerong.wedle.school.repository.SchoolRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final SchoolRepository schoolRepository;

    public void createCommunityIfNotExists(SchoolRequest schoolRequest) {
        School school = schoolRepository.findById(schoolRequest.getId()).orElseThrow(SchoolNotFoundException::new);

        boolean exists = communityRepository.existsBySchool(school);
        if (!exists) {
            Community community = new Community(school);
            communityRepository.save(community);
        }
    }
}
