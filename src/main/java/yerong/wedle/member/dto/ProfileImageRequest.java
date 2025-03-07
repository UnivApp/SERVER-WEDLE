package yerong.wedle.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileImageRequest {

    @Schema(description = "프로필 사진", required = true)
    private MultipartFile profileImage;
}
