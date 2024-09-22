package yerong.wedle.category.activity.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ActivityImage {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "activity_image_id")
    private Long activityImageId;

    @Column(nullable = false)
    private String imageUrl;
    private String source;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    public ActivityImage(String imageUrl, String source, Activity activity) {
        this.imageUrl = imageUrl;
        this.source = source;
        this.activity = activity;
    }
}