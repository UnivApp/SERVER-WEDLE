package yerong.wedle.category.expo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.category.expo.domain.Expo;
import yerong.wedle.category.expo.domain.ExpoStatus;
import yerong.wedle.category.expo.domain.ExpoType;
import yerong.wedle.university.domain.University;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public interface ExpoRepository extends JpaRepository<Expo, Long> {

    List<Expo> findByExpoType(ExpoType type);

    List<Expo> findByExpoStatus(ExpoStatus status);

    List<Expo> findByEndDateBeforeAndExpoStatus(LocalDate today, ExpoStatus expoStatus);

    List<Expo> findByContentContainingOrTitleContaining(String content, String title);
}
