package yerong.wedle.category.event.domain;

import jakarta.persistence.*;
import yerong.wedle.university.domain.University;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    private String name;
    private String description;
    private LocalDateTime date;
    private String location;

    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;
}
