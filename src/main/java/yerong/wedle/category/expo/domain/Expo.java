package yerong.wedle.category.expo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;
import yerong.wedle.category.activity.domain.ActivityUniversity;
import yerong.wedle.university.domain.University;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@Entity
public
class Expo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expo_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExpoType expoType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExpoStatus expoStatus;

    private String expoYear;

    @Column(nullable = false)
    private String title;

    private String link;

    private String location;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDate startDate;
    private LocalDate endDate;

    public void setExpoStatus(ExpoStatus expoStatus) {
        this.expoStatus = expoStatus;
    }
}
