package yerong.wedle.common.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@Slf4j
public class FMCConfig {

    @Value("${firebase.config}")
    private String firebaseConfig;

    @PostConstruct
    private void init() {
        try (ByteArrayInputStream serviceAccount = new ByteArrayInputStream(firebaseConfig.getBytes())) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
            log.info("파이어베이스 서버와의 연결에 성공했습니다.");
        } catch (IOException e) {
            log.error("파이어베이스 서버와의 연결에 실패했습니다.", e);
        }
    }
}
