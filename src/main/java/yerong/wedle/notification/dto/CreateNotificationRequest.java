package yerong.wedle.notification.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNotificationRequest {
    @NotNull
    private LocalDateTime notificationTime;

    @NotNull
    private Long eventId;

    @NotNull
    private List<String> registrationTokens;
}
