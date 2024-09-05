package yerong.wedle.category.restaurant.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Getter
@Entity
public class MenuItem {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "menu_item_id")
    private Long menuItemId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String price;

    @Column
    private String imageUrl;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private MenuCategory category;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

}
