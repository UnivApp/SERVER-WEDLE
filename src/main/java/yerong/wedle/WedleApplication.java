package yerong.wedle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WedleApplication {

	public static void main(String[] args) {
		SpringApplication.run(WedleApplication.class, args);
	}

}
