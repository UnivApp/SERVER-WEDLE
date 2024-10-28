package yerong.wedle.notification.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yerong.wedle.notification.dto.CreateNotificationRequest;
import yerong.wedle.notification.dto.NotificationResponse;
import yerong.wedle.notification.service.NotificationService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationApiController {
    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(@RequestBody @Valid CreateNotificationRequest request){

        NotificationResponse notificationResponse = notificationService.createNotification(request);
        return ResponseEntity.ok(notificationResponse);
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getNotifications(@RequestParam LocalDate date) {
        List<NotificationResponse> notificationResponses = notificationService.getNotificationsByDate(date);
        return ResponseEntity.ok(notificationResponses);
    }
}
