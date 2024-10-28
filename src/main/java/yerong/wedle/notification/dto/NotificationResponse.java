package yerong.wedle.notification.dto;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class NotificationResponse {

    private Long notificationId;
    private LocalDateTime notificationTime;
    private Long eventId;
    private List<String> registrationTokens;
    private boolean isActive;
}
