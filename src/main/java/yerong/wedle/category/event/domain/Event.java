package yerong.wedle.category.event.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.university.domain.University;

import java.time.LocalDateTime;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private EventType eventType;


    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "university_id")
    private University university;

    @OneToOne(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private EventDetails eventDetails;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EventImage> photos;
}
