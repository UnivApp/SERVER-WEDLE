package yerong.wedle.notification.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.units.qual.N;

@Getter
@Setter
public class CreateNotificationRequest {
    @NotNull
    private LocalDate notificationDate;;

    @NotNull
    private Long eventId;

    @NotNull
    private List<String> registrationTokens;
}
