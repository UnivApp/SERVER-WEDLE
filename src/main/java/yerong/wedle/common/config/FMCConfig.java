package yerong.wedle.common.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class FMCConfig {

    @PostConstruct
    private void init() throws IOException {
        String fileResourceURL = "security/wedle-94dad-firebase-adminsdk-3qavr-19fe7c2a51.json";
        ClassPathResource resource = new ClassPathResource(fileResourceURL);

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
                .build();

        FirebaseApp.initializeApp(options);
    }
}
