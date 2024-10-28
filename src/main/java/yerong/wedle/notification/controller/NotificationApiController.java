package yerong.wedle.notification.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yerong.wedle.notification.dto.CreateNotificationRequest;
import yerong.wedle.notification.dto.NotificationResponse;
import yerong.wedle.notification.service.NotificationService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationApiController {
    private final NotificationService notificationService;

    @Operation(summary = "알림 생성", description = "이벤트에 대한 새로운 알림을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "알림이 성공적으로 생성되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터")
    })

    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(@RequestBody @Valid CreateNotificationRequest request){

        NotificationResponse notificationResponse = notificationService.createNotification(request);
        return ResponseEntity.ok(notificationResponse);
    }

    @Operation(summary = "알림 삭제", description = "ID를 통해 특정 알림을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "알림이 성공적으로 삭제되었습니다."),
            @ApiResponse(responseCode = "404", description = "알림을 찾을 수 없습니다.")
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteNotification(@RequestParam Long notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.noContent().build();
    }
}
