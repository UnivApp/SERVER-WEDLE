package yerong.wedle.category.restaurant.domain;

import jakarta.persistence.*;
import yerong.wedle.university.domain.University;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "restaurant_id")
    private Long restaurantId;

    private String name;
    private String description;
    private String location;

    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;
}
