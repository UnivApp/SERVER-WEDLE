package yerong.wedle.category.announcement.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.university.domain.University;

import java.util.Set;

@Getter
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@Entity
public
class AdmissionAnnouncement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announcement_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnnouncementCategory category;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String link;

    @Column
    private String source;

    @ManyToMany
    @JoinTable(
            name = "announcement_university",
            joinColumns = @JoinColumn(name = "announcement_id"),
            inverseJoinColumns = @JoinColumn(name = "university_id")
    )
    private Set<University> relatedUniversities;
}
