package yerong.wedle.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yerong.wedle.common.s3.S3Service;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.dto.GradeAndClassRegistrationRequest;
import yerong.wedle.member.dto.NicknameDuplicateResponse;
import yerong.wedle.member.dto.NicknameRequest;
import yerong.wedle.member.dto.NicknameResponse;
import yerong.wedle.member.dto.ProfileImageResponse;
import yerong.wedle.member.exception.ExistingNicknameException;
import yerong.wedle.member.exception.InvalidNicknameException;
import yerong.wedle.member.exception.MemberNicknameDuplicateException;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.repository.MemberRepository;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final S3Service s3Service;
    @Value("${cloud.aws.s3.defaultImage}")
    String defaultImageUrl;

    private boolean isNicknameDuplicate(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    private void validateNickname(String nickname) {
        if (nickname == null || nickname.trim().isEmpty() || nickname.contains(" ")) {
            throw new InvalidNicknameException();
        }
    }

    public NicknameResponse setNickname(NicknameRequest nicknameRequest) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);

        validateNickname(nicknameRequest.getNickName());

        if (member.getNickname() != null && member.getNickname().equals(nicknameRequest.getNickName())) {
            throw new ExistingNicknameException();
        }

        if (isNicknameDuplicate(nicknameRequest.getNickName())) {
            throw new MemberNicknameDuplicateException();
        }

        member.setNickname(nicknameRequest.getNickName());
        memberRepository.save(member);

        return NicknameResponse.builder()
                .nickName(member.getNickname()).build();
    }

    public NicknameResponse getNickname() {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);

        return NicknameResponse.builder()
                .nickName(member.getNickname()).build();
    }

    public NicknameDuplicateResponse checkNicknameDuplicate(String nickname) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);

        String message = "";
        boolean isDuplicate = false;

        if (member.getNickname() != null && member.getNickname().equals(nickname)) {
            message = "기존 닉네임과 동일합니다.";
            isDuplicate = true;
        } else if (isNicknameDuplicate(nickname)) {
            message = "이미 존재하는 닉네임입니다.";
            isDuplicate = true;
        } else {
            message = "사용 가능한 닉네임입니다.";
            isDuplicate = false;
        }

        return NicknameDuplicateResponse.builder()
                .isDuplicate(isDuplicate)
                .message(message)
                .build();
    }

    public void setGradeAndClass(GradeAndClassRegistrationRequest gradeAndClassRegistrationRequest) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);

        member.setGradeAndClass(gradeAndClassRegistrationRequest.getGrade(),
                gradeAndClassRegistrationRequest.getClassName());
        memberRepository.save(member);
    }

    public ProfileImageResponse setProfileImage(MultipartFile profileImageRequest) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);

        if (member.getProfileImageUrl() != null && !member.getProfileImageUrl().equals(defaultImageUrl)) {
            s3Service.deleteFile(member.getProfileImageUrl());
        }

        String imageUrl = s3Service.uploadProfileImage(profileImageRequest);
        member.setProfileImageUrl(imageUrl);
        memberRepository.save(member);
        return new ProfileImageResponse(imageUrl);
    }

    public void removeProfileImage() {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);
        if (member.getProfileImageUrl() != null && !member.getProfileImageUrl().equals(defaultImageUrl)) {
            s3Service.deleteFile(member.getProfileImageUrl());
        }
        member.setProfileImageUrl(defaultImageUrl);
        memberRepository.save(member);
    }


    private String getCurrentUserId() {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();

        return socialId;
    }
}
