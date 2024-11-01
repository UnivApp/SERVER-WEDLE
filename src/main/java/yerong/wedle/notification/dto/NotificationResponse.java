package yerong.wedle.notification.dto;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class NotificationResponse {

    private Long notificationId;
    private String title;
    private String type;
    private LocalDate notificationDate;
    private Long eventId;
    private List<String> registrationTokens;
    private boolean isActive;
}
