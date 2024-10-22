package yerong.wedle.notification.domain;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.calendar.domain.CalendarEvent;
import yerong.wedle.common.domain.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Notification extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @Column(nullable = false)
    private LocalDateTime notificationTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private CalendarEvent event;

    @ElementCollection
    private List<String> registrationTokens;

    @Column(nullable = false)
    private boolean isActive;


    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
