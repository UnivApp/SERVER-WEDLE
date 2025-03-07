package yerong.wedle.common.s3;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@RequiredArgsConstructor
@Service
public class S3Service {

    private final S3Client s3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    @Value("${cloud.aws.s3.url}")
    private String url;

    public String uploadProfileImage(MultipartFile file) {
        String fileName = "profileImage/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
        String contentType = getContentType(file.getOriginalFilename());

        try {
            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .contentType(contentType)
                            .build(),
                    RequestBody.fromByteBuffer(ByteBuffer.wrap(file.getBytes())));
            return url + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload profile image", e);
        }
    }

    public void deleteFile(String profileImageUrl) {
        String fileName = profileImageUrl.replace(url, "");

        try {
            s3Client.deleteObject(builder -> builder.bucket(bucketName).key(fileName));
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete profile image", e);
        }
    }

    private String getContentType(String fileName) {
        if (fileName.endsWith(".png") || fileName.endsWith(".PNG")) {
            return "image/png";
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".JPG")
                || fileName.endsWith(".JPEG")) {
            return "image/jpeg";
        } else {
            throw new RuntimeException("Unsupported file type");
        }
    }
}
