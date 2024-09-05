package yerong.wedle.category.restaurant.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Getter
@Entity
public class RestaurantPhoto {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "photo_id")
    private Long photoId;

    @Column(nullable = false)
    private String photoUrl;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
